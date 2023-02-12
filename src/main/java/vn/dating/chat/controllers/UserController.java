package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.mapper.UserMapper;
import vn.dating.chat.model.User;
import vn.dating.chat.services.UserService;
import vn.dating.chat.utils.PagedResponse;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping()
    public PagedResponse getListUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUser(page,size);
    }
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserMapper.toGetUser(user), HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity getUserByEmail(@RequestParam("email") String  email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserMapper.toGetUser(user.get()), HttpStatus.OK);
    }

    @GetMapping("/contact")
    public ResponseEntity getContactByEmail(@RequestParam("email") String  email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserMapper.toGetContact(user.get()), HttpStatus.OK);
    }
}
