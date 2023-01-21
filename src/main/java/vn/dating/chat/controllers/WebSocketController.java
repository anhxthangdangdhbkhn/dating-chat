package vn.dating.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.dating.chat.dto.*;
import vn.dating.chat.model.Message;
import vn.dating.chat.model.User;
import vn.dating.chat.service.MessageService;
import vn.dating.chat.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public  class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public OutputMessage WebSocketController(MessageNewDto message) {
        System.out.println("client");
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage( message.getName()  , message.getMessage(),time);
    }

    @MessageMapping("/chat")
    public void processMessage(SendMessage message) {

       System.out.println("chat " + message.getSendto());
        System.out.println("chat " + message.getMessage());

//        messagingTemplate.convertAndSendToUser(message.getSendto(),"/queue/messages",
//                new ChatNotification("abc", message.getSendto(),message.getMessage()));

    }

    @MessageMapping("/message")
    public void chat(MessageDto message) {

//        User fromUser = userService.findById(message.getSenderId()).orElse(null);
//        User toUser = userService.findById(message.getRecipientId()).orElse(null);
//
//        Message newMessage = new Message();
//        newMessage.setSender(fromUser);
//        newMessage.setRecipient(toUser);
//        newMessage.setContent(message.getContent());
//
//        messageService.save(newMessage);

        System.out.println(message.toString());

//        messagingTemplate.convertAndSendToUser(message.getRecipientId(),"/queue/messages",
//                new ChatNotification("abc", message.getSendto(),message.getMessage()));

    }


}