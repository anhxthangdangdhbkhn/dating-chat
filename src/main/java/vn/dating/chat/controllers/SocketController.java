package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import vn.dating.chat.dto.messages.*;
import vn.dating.chat.model.Group;
import vn.dating.chat.model.User;
import vn.dating.chat.services.*;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Controller
@Slf4j
public  class SocketController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private GroupService groupService;


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
    public ConfirmPrivateMessage privateMessage(MessagePrivateDto message, Principal principal,SimpMessageHeaderAccessor headerAccessor) {

        log.info("From {}",principal.getName());
        log.info("to {}",message.getRecipientId());

        message.setSenderId(principal.getName());
        message.setCreatedAt(Instant.now());
        message.setUpdatedAt(Instant.now());

        String sessionId = headerAccessor.getSessionId();
        log.info("sessionId: ");
        log.info(sessionId);

        messageService.sendPrivateToUser(message);

        return new ConfirmPrivateMessage(message);
    }

    @MessageMapping("/group/{groupId}/sendMessage")
    public void sendMessageToGroup(@DestinationVariable("groupId") Long groupId, MessageDto messageDto, Principal principal) {

        User user = userService.getUserByEmail(principal.getName());
        Group group = groupService.getGroupById(groupId);
        String content = messageDto.getContent();

        messageService.sendMessageToGroup(groupId,messageDto);
    }

//    @MessageMapping("/chat/leave")
//    public void leaveChatRoom(@Payload ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor) {
//        String chatRoomId = chatMessageDto.getChatRoomId();
//        String sessionId = headerAccessor.getSessionId();
//
//        chatRoomService.removeUserFromChatRoom(chatRoomId, sessionId);
//
//        chatMessageDto.setContent(chatMessageDto.getSender() + " has left the chat room.");
////        messagingTemplate.convertAndSend("/topic/group/" + chatRoomId, chatMessage);
//    }


}