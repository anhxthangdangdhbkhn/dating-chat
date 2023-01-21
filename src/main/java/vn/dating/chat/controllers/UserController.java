package vn.dating.chat.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.UserDto;
import vn.dating.chat.model.User;
import vn.dating.chat.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity saveUser(@RequestBody UserDto userDto) {

        String userName = userDto.getUsername();
        String email = userDto.getEmail();

        User user = new User();
        user.setEmail(email);
        user.setUsername(userName);

        user = userService.save(user);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
