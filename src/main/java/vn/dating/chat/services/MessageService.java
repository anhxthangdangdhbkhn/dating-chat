package vn.dating.chat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.messages.MessageDto;
import vn.dating.chat.dto.messages.MessagePrivateDto;
import vn.dating.chat.model.User;

import java.util.List;


@Service
@Slf4j
public class MessageService {


    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    public void sendPrivateToUser( MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getRecipientId(),"/topic/private-messages", newMessage);
    }

    public void sendPrivateMe( MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getSenderId(),"/topic/private-messages", newMessage);
    }

    public void sendMessageToGroup(Long groupId, MessageDto messageDto) {
        messagingTemplate.convertAndSend("/topic/group-private-messages/" + groupId, messageDto);
    }

}
