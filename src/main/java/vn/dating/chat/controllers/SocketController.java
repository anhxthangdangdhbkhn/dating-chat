package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import vn.dating.chat.dto.messages.api.ResultGroupMembersOfGroupDto;
import vn.dating.chat.dto.messages.api.ResultGroupMessage;
import vn.dating.chat.dto.messages.socket.*;
import vn.dating.chat.model.*;
import vn.dating.chat.services.*;


import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
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


    @MessageMapping("/group-private-messages")
    @SendToUser("topic/group-private-messages")
    public ResultGroupMessage sendMessageToGroup(MessagePrivateGroupDto messagePrivateGroupDto, Principal principal) {
        log.info("group-private-messages");
        ResultGroupMessage resultGroupMessage = messageService.sendMessageToGroup(messagePrivateGroupDto,principal);

        return resultGroupMessage;
    }

    @MessageMapping("/create-group-messages")
    @SendToUser("topic/create-group-messages")
    public ResultGroupMembersOfGroupDto createNewGroup(MessageCreateGroupDto messageCreateGroupDto, Principal principal) {
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
                        ResultGroupMembersOfGroupDto resultGroupMembersOfGroupDto = groupService.createGroup(group,userList, currentUser, GroupMemberType.PRIVATE);
                        log.info("Created new group {}", resultGroupMembersOfGroupDto.toString());

                        String content = messageCreateGroupDto.getContent();
                        MessagePrivateGroupOutputDto messageOutputDto = new MessagePrivateGroupOutputDto();

                        messageOutputDto.setContent(content);
                        messageOutputDto.setGroupId(group.getId());
                        messageOutputDto.setSenderId(currentUser.getEmail());
                        messageOutputDto.setTime(messageCreateGroupDto.getTime());

//                        messageService.sendMessageToGroup(messageOutputDto,principal);

//                        messageService.sendMessageCreatedGroup(messageOutputDto,principal);

                        return resultGroupMembersOfGroupDto;
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