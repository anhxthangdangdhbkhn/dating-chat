package vn.dating.chat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.messages.api.ResultGroupDto;
import vn.dating.chat.dto.messages.socket.MessagePrivateGroupDto;
import vn.dating.chat.dto.messages.socket.MessagePrivateDto;
import vn.dating.chat.dto.messages.socket.MessagePrivateGroupOutputDto;
import vn.dating.chat.model.*;
import vn.dating.chat.repositories.GroupMemberRepository;
import vn.dating.chat.repositories.GroupRepository;
import vn.dating.chat.repositories.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class MessageService {
    @Autowired
    private GroupRepository groupRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserReceiveService userReceiveService;

    @Autowired
    private EntityManager entityManager;





    public void sendPrivateToUser( MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getRecipientId(),"/topic/private-messages", newMessage);
    }

    public void sendPrivateMember (MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getSenderId(),"/topic/private-member", newMessage);
    }

    public void sendPrivateNotification (MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getSenderId(),"/topic/private-notification", newMessage);
    }

    public List<User> getUsersInGroup(Long groupId) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT gm.user FROM GroupMember gm " +
                        "WHERE gm.group.id = :groupId",
                User.class);
        query.setParameter("groupId", groupId);
        List<User> result = query.getResultList();
        return result;
    }

    public List<String> getAllUserOfGroup(long groupId){

        log.info("groupId:" +groupId );
        List<GroupMember> groupMemberList = groupMemberRepository.findByGroupId(groupId);
        log.info("Member group size: ",groupMemberList.size());
        List<String> listUsers = new ArrayList<>();
        groupMemberList.forEach(m->{
            listUsers.add(m.getUser().getEmail());
        });

        return  listUsers;
    }

    public void sendMessageToGroup(MessagePrivateGroupOutputDto messagePrivateGroupOutputDto, Principal principal) {

//        List<String> listUsers = getAllUserOfGroup(messagePrivateGroupOutputDto.getGroupId());

        log.info(messagePrivateGroupOutputDto.toString());

        List<User> userList = getUsersInGroup(messagePrivateGroupOutputDto.getGroupId());
        Group currentGroup = groupRepository.findById(messagePrivateGroupOutputDto.getGroupId()).orElse(null);


        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getEmail().contains(principal.getName())){
                userList.remove(userList.get(i));
                break;
            }
        }

        Message message = new Message();
        message.setContent(message.getContent());
        message.setDelete(false);
        message.setSender(userService.getUserByEmail(principal.getName()));
        message.setContent(messagePrivateGroupOutputDto.getContent());
        message.setGroup(currentGroup);
        message = messageRepository.save(message);

        for(int index =0;index < userList.size();index++){
            UserReceive userReceive = new UserReceive();
            userReceive.setDelete(false);
            userReceive.setCreatedAt(Instant.now());
            userReceive.setUserReceive(userList.get(index));
            userReceive.setReceiveChat(message);
            userReceiveService.save(userReceive);
        }




        for(int i=0;i<userList.size();i++){
            log.info("sent to user " +userList.get(i).getEmail());
            messagingTemplate.convertAndSendToUser(userList.get(i).getEmail(),"/topic/group-private-messages", messagePrivateGroupOutputDto);
        }
    }

    public void sendMessageCreatedGroup(ResultGroupDto resultGroupDto, Principal principal) {

//        List<String> listUsers = getAllUserOfGroup(messagePrivateGroupOutputDto.getGroupId());
//        if(listUsers.contains(principal.getName())){
//            listUsers.remove(principal.getName());
//        }
//
//        for(int i=0;i<listUsers.size();i++){
//            log.info("sent to user " +listUsers.get(i));
//            messagingTemplate.convertAndSendToUser(listUsers.get(i),"/topic/create-group-messages", resultGroupDto);
//        }
    }
}
