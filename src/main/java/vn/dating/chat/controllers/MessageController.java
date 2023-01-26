package vn.dating.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import vn.dating.chat.dto.*;
import vn.dating.chat.service.MessageService;
import vn.dating.chat.service.NotificationService;
import vn.dating.chat.service.UserService;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
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

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public  GreetingOutput processMessage(SendMessage message) {
       System.out.println("/topic/messages");
        notificationService.sendGlobalNotification();
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new GreetingOutput(message.getName(),message.getMessage(),time);
    }

    @MessageMapping("/private-message")
    @SendToUser("topic/private-message")
    public ResponseMessage privateMessage(MessageReplyDto message, Principal principal) {
//        User fromUser = userService.findById(message.getSenderId()).orElse(null);
//        User toUser = userService.findById(message.getRecipientId()).orElse(null);
//
//        Message newMessage = new Message();
//        newMessage.setSender(fromUser);
//        newMessage.setRecipient(toUser);
//        newMessage.setContent(message.getContent());
//
//        messageService.save(newMessage);
        //        messagingTemplate.convertAndSendToUser(message.getRecipientId(),"/queue/messages",
//                new ChatNotification("abc", message.getSendto(),message.getMessage()));




        messageService.sendPrivateToUser(message);
        messageService.sendPrivateMe(message);
        return new ResponseMessage(HtmlUtils.htmlEscape(
                "Sending private message to user " + principal.getName() + ": "
                        + message.getContent())
        );



    }


}