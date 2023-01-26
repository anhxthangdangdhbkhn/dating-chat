package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import vn.dating.chat.dto.*;
import vn.dating.chat.service.MessageService;
import vn.dating.chat.service.NotificationService;
import vn.dating.chat.service.UserService;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Slf4j
public  class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MessageService messageService;


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingOutput WebSocketController(Greeting greeting) {
        System.out.println("greeting");
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new GreetingOutput( greeting.getName()  , greeting.getMessage(),time);
    }

    @MessageMapping("/public-messages")
    @SendTo("/topic/public-messages")
    public  GreetingOutput processMessage(MessagePublicDto message) {
       System.out.println("/topic/messages");
        notificationService.sendGlobalNotification();
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new GreetingOutput(message.getName(),message.getMessage(),time);
    }

    @MessageMapping("/private-messages")
    @SendToUser("topic/private-messages")
    public ResponsePrivateMessage privateMessage(MessagePrivateDto message, Principal principal) {

        log.info("From {}",principal.getName());
        boolean status =  messageService.sendPrivateToUser(message);

        return new ResponsePrivateMessage(message.getSenderId(), message.getContent(),status);
    }
}