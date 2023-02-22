package vn.dating.chat.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.services.UserService;


@RestController
@RequestMapping("/api/v1/messages")
@Slf4j
public class MessageController {
    @Autowired
    private UserService userService;

}
