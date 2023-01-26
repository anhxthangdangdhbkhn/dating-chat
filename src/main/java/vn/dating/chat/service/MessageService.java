package vn.dating.chat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.MessagePrivateDto;
import vn.dating.chat.model.Message;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.MessageRepository;

import java.time.Instant;


@Service
@Slf4j
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public boolean sendPrivateToUser( MessagePrivateDto newMessage) {

        User from = userService.findByUserName(newMessage.getSenderId()).orElse(null);
        User to = userService.findByUserName(newMessage.getRecipientId()).orElse(null);

        if(from==null){
            log.info("From user is null");
            return false;
        }
        if(to==null){
            log.info("To user is null");
            return false;
        }

        Message message = new Message();
        message.setSender(from);
        message.setRecipient(to);
        message.setContent(newMessage.getContent());
        message.setCreatedAt(Instant.now());
        message.setUpdatedAt(Instant.now());
        save(message);

        messagingTemplate.convertAndSendToUser(newMessage.getRecipientId(),"/topic/private-messages", newMessage);
        return true;
    }

    public void sendPrivateMe( MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getSenderId(),"/topic/private-messages", newMessage);
    }


    void save(Message message){messageRepository.save(message);}






}
