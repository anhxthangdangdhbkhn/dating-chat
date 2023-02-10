package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.auth.AuthLoginDto;
import vn.dating.chat.dto.auth.JwtAuthenticationResponse;
import vn.dating.chat.dto.auth.RegisterNewDto;
import vn.dating.chat.dto.auth.UserProfile;
import vn.dating.chat.exceptions.ResourceNotFoundException;
import vn.dating.chat.model.User;
import vn.dating.chat.securities.JwtTokenProvider;
import vn.dating.chat.securities.UserLogged;
import vn.dating.chat.services.UserService;


import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {


    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @Autowired
    private JwtTokenProvider tokenProvider;



//    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')")
//    @GetMapping(value = "/p/current")
//    @ResponseBody
//    public ResponseEntity current() {
//         return ResponseEntity.ok(null);
//    }


    @ResponseBody
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthLoginDto authLoginDto) {

        String email = authLoginDto.getEmail();
        String password = authLoginDto.getPassword();
        log.info("Email {}",email);
        log.info("Password {}",password);

        User userGet = userService.findByEmail(email).orElseThrow(()-> {
            throw new ResourceNotFoundException("user","email",email);});

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authLoginDto.getEmail(), authLoginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserProfile userProfile = new UserProfile();


        String token = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token,refreshToken, userProfile.userProfileConvert(userGet)));
    }

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<?> refreshToken() {

        UserLogged userLogged = new UserLogged();
        String email = userLogged.getEmail();
        User user = userService.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "Email", ""));
        String password = user.getPassword();


        User userGet = userService.findByEmail(email).orElseThrow(()-> {
            throw new ResourceNotFoundException("user","email",email);});
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserProfile userProfile = new UserProfile();


        String token = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token,refreshToken, userProfile.userProfileConvert(userGet)));
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
}
