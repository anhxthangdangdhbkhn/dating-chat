package vn.dating.chat.controllers;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.auth.*;
import vn.dating.chat.exceptions.ResourceNotFoundException;
import vn.dating.chat.mapper.AuthMapper;
import vn.dating.chat.model.Token;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.TokenRepository;
import vn.dating.chat.securities.JwtTokenProvider;
import vn.dating.chat.securities.UserLogged;
import vn.dating.chat.services.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {


    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;


    @Autowired
    private JwtTokenProvider tokenProvider;



//    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')")
//    @GetMapping(value = "/p/current")
//    @ResponseBody
//    public ResponseEntity current() {
//         return ResponseEntity.ok(null);
//    }


    @ResponseBody
    @PostMapping(value = "/login2")
    public ResponseEntity loginUser2(@Valid @RequestBody AuthLoginDto authLoginDto, HttpServletRequest request) {

        String email = authLoginDto.getEmail();
        String password = authLoginDto.getPassword();
        log.info("Email {}",email);
        log.info("Password {}",password);


        User currentUser = userService.findByEmail(email).orElseThrow(()-> {
            throw new ResourceNotFoundException("user","email",email);});

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authLoginDto.getEmail(), authLoginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthDto authDto = tokenProvider.generateConnect(currentUser,authentication);

        if(authDto==null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(authDto);
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthLoginDto authLoginDto, HttpServletRequest request) {

        String email = authLoginDto.getEmail();
        String password = authLoginDto.getPassword();
        log.info("Email {}",email);
        log.info("Password {}",password);

//        String ipAddress = request.getRemoteAddr();
//        String location = "";
//        try {
//            // Load the MaxMind GeoIP database
//            DatabaseReader dbReader = new DatabaseReader.Builder(getClass().getResourceAsStream("/GeoLite2-City.mmdb")).build();
//            // Get the location information for the IP address
//            CityResponse response = dbReader.city(InetAddress.getByName(ipAddress));
//            location = response.getCity().getName() + ", " + response.getCountry().getName();
//            log.info(location);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        User currentUser = userService.findByEmail(email).orElseThrow(()-> {
            throw new ResourceNotFoundException("user","email",email);});

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authLoginDto.getEmail(), authLoginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserProfile userProfile = new UserProfile();

        AuthDto authDto = tokenProvider.generateConnect(currentUser,authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(authDto.getAccessToken(), authDto.getRefreshToken(), currentUser.getAvatar(),userProfile.userProfileConvert(currentUser)));
    }

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<?> refreshToken() {

        UserLogged userLogged = new UserLogged();
        String email = userLogged.getEmail();
        User user = userService.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "Email", ""));
        String password = user.getPassword();

        User currentUser = userService.findByEmail(email).orElseThrow(()-> {
            throw new ResourceNotFoundException("user","email",email);});
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserProfile userProfile = new UserProfile();

        AuthDto authDto = tokenProvider.generateConnect(currentUser,authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(authDto.getAccessToken(),authDto.getRefreshToken(),user.getAvatar(), userProfile.userProfileConvert(currentUser)));
    }

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterNewDto registerNewDto) {
        String email = registerNewDto.getEmail();

        log.info("Email {}",email);

        User userGet = userService.findByEmail(email).orElse(null);
        if(userGet !=null) return new ResponseEntity<>("Email is exist", HttpStatus.BAD_REQUEST);;

        // Creating user's account
        String username = registerNewDto.getUsername();
        String password = registerNewDto.getPassword();

        User user = new User();
        user.newUser(email,username,password);

        User newUser = userService.createUserSendEmailActiveAccount(user);


        if (newUser != null) {
            return new ResponseEntity("Created user and sent active", HttpStatus.OK);
        }

        return new ResponseEntity("Your register Failed", HttpStatus.EXPECTATION_FAILED);
    }

    @RequestMapping(value = "/verify/{token}", method = RequestMethod.GET)
    public ResponseEntity  verify(@PathVariable("token") String token){

        User getUser = userService.findByCreateToken(token).orElseThrow(()-> {
            throw new ResourceNotFoundException("User","createToken",token);});

        if(userService.userVerify(getUser)){
            return new ResponseEntity<>("Verify is ok", HttpStatus.OK);
        }

        return new ResponseEntity<>("verify is false", HttpStatus.OK);
    }

    @RequestMapping(value = "/deviceAccess", method = RequestMethod.GET)
    public ResponseEntity  deviceAccess(Principal principal){

        if(principal ==null){
            return ResponseEntity.badRequest().build();
        }

        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if(currentUser ==null){
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity(AuthMapper.toGetListAccess(currentUser.getTokens()),HttpStatus.OK);
    }

    @RequestMapping(value = "/deviceAccess/{tokenId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDeviceAccess(@PathVariable("tokenId") Long tokenId, Principal principal) {

        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }

        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (currentUser == null) {
            return ResponseEntity.badRequest().build();
        }

        Token tokenToDelete = tokenRepository.findById(tokenId).orElse(null);

        if (tokenToDelete == null) {
            return ResponseEntity.notFound().build();
        }

        if (!tokenToDelete.getUserToken().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        tokenRepository.delete(tokenToDelete);

        return ResponseEntity.ok("Device access deleted successfully");
    }

    @RequestMapping(value = "/logout/{token}", method = RequestMethod.DELETE)
    public ResponseEntity<String> logout(@PathVariable("token") String token,Principal principal) {

        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }

        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (currentUser == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Token> lists =  tokenRepository.findByAccessTokenOrRefreshToken(token,token);
        if(lists.size()==0) return ResponseEntity.badRequest().build();


        tokenRepository.deleteByAccessTokenOrRefreshToken(token,token);



//        tokenService.deleteAllByUser(currentUser);

        return ResponseEntity.ok("Logged out successfully");
    }

}
