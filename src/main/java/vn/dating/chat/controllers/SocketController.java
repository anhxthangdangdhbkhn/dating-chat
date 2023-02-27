package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import vn.dating.chat.dto.messages.api.ResultGroupDto;
import vn.dating.chat.dto.messages.socket.*;
import vn.dating.chat.model.*;
import vn.dating.chat.services.*;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private GroupMemberService groupMemberService;


//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public GreetingOutput WebSocketController(Greeting greeting) {
//        System.out.println("greeting");
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        return new GreetingOutput( greeting.getName()  , greeting.getMessage(),time);
//    }
//
//    @MessageMapping("/public-messages")
//    @SendTo("/topic/public-messages")
//    public  GreetingOutput processMessage(MessagePublicDto message) {
//       System.out.println("/topic/messages");
//        notificationService.sendGlobalNotification();
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        return new GreetingOutput(message.getName(),message.getMessage(),time);
//    }
//
//    @MessageMapping("/private-messages")
//    @SendToUser("topic/private-messages")
//    public ConfirmPrivateMessage privateMessage(MessagePrivateDto message, Principal principal, SimpMessageHeaderAccessor headerAccessor) {
//
//        log.info("From {}",principal.getName());
//        log.info("to {}",message.getRecipientId());
//        message.setSenderId(principal.getName());
//        message.setCreatedAt(Instant.now());
//        message.setUpdatedAt(Instant.now());
//        messageService.sendPrivateToUser(message);
//
//        return new ConfirmPrivateMessage(message);
//    }

    @MessageMapping("/group-private-messages")
    @SendToUser("topic/group-private-messages")
    public MessagePrivateGroupOutputDto sendMessageToGroup(MessagePrivateGroupDto messagePrivateGroupDto, Principal principal) {
            log.info("group-private-messages");
            String username = principal.getName();
            String content = messagePrivateGroupDto.getContent();
            MessagePrivateGroupOutputDto messageOutputDto = new MessagePrivateGroupOutputDto();

            messageOutputDto.setContent(content);
            messageOutputDto.setGroupId(messagePrivateGroupDto.getGroupId());
            messageOutputDto.setSenderId(username);
            messageOutputDto.setTime(messagePrivateGroupDto.getTime());

            messageService.sendMessageToGroup(messageOutputDto,principal);

            return messageOutputDto;
    }

    @MessageMapping("/create-group-messages")
    @SendToUser("topic/create-group-messages")
    public ResultGroupDto createNewGroup(MessageCreateGroupDto messageCreateGroupDto, Principal principal) {
        log.info("group-private-messages");
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);



        if(currentUser==null) return  null;

        if(messageCreateGroupDto.getType() == GroupType.PRIVATE){
            List<Long> listGroup = groupService.existChatTwoUser(principalName, messageCreateGroupDto.getWith());
            if(listGroup==null){
                List<String> members = new ArrayList<>();
                members.add(principalName);
                members.add(messageCreateGroupDto.getWith());

                List<User> userList = userService.findUsersByEmails(members);
                if(members.size() == userList.size()){
                    Group group = new Group();
                    group.setAdmin(currentUser);
                    group.setCreatedAt(Instant.now());
                    group.setType(GroupType.PRIVATE);
                    group.setRandom(GroupRandomType.NONE);
                    group.generateUrl();;
                    group.setName("PRIVATE");

                    group = groupService.saveGroup(group);
                    if(group==null){
                       return null;
                    }else{
                        ResultGroupDto resultGroupDto = groupService.createGroup(group,userList, currentUser, GroupMemberType.PRIVATE);
                        log.info("Created new group {}",resultGroupDto.toString());

                        String content = messageCreateGroupDto.getContent();
                        MessagePrivateGroupOutputDto messageOutputDto = new MessagePrivateGroupOutputDto();

                        messageOutputDto.setContent(content);
                        messageOutputDto.setGroupId(group.getId());
                        messageOutputDto.setSenderId(currentUser.getEmail());
                        messageOutputDto.setTime(messageCreateGroupDto.getTime());

                        messageService.sendMessageToGroup(messageOutputDto,principal);

//                        messageService.sendMessageCreatedGroup(messageOutputDto,principal);

                        return  resultGroupDto;
                    }

                }else {

                }
            }
            if(listGroup.size()==1){
                long groupId = listGroup.get(0);
                return groupService.getChatInfoGroup(groupId,currentUser);
            }
            return null;


        } else if (messageCreateGroupDto.getType() ==GroupType.RANDOM) {

        } else if (messageCreateGroupDto.getType() == GroupType.PUBLIC) {

        }


        return null;
    }
}