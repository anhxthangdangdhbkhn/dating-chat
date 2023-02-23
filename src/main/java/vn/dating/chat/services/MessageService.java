package vn.dating.chat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.messages.socket.MessagePrivateGroupDto;
import vn.dating.chat.dto.messages.socket.MessagePrivateDto;
import vn.dating.chat.dto.messages.socket.MessagePrivateGroupOutputDto;
import vn.dating.chat.model.GroupMember;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.GroupMemberRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class MessageService {


    @Autowired
    private UserService userService;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    public void sendPrivateToUser( MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getRecipientId(),"/topic/private-messages", newMessage);
    }

    public void sendPrivateMember (MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getSenderId(),"/topic/private-member", newMessage);
    }

    public void sendPrivateNotification (MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getSenderId(),"/topic/private-notification", newMessage);
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

        List<String> listUsers = getAllUserOfGroup(messagePrivateGroupOutputDto.getGroupId());
        if(listUsers.contains(principal.getName())){
            listUsers.remove(principal.getName());
        }

        for(int i=0;i<listUsers.size();i++){
            log.info("sent to user " +listUsers.get(i));
            messagingTemplate.convertAndSendToUser(listUsers.get(i),"/topic/group-private-messages", messagePrivateGroupOutputDto);
        }
    }
}
