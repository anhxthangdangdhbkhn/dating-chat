package vn.dating.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.MessageReplyDto;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.MessageRepository;
import vn.dating.chat.repositories.UserRepository;


@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendPrivateToUser( MessageReplyDto messageReplyDto) {

//        User from = findById(messageReplyDto.getSenderId())

        messagingTemplate.convertAndSendToUser(messageReplyDto.getRecipientId(),"/topic/private-messages", messageReplyDto);
    }

    public void sendPrivateMe( MessageReplyDto messageReplyDto) {
        messagingTemplate.convertAndSendToUser(messageReplyDto.getSenderId(),"/topic/private-messages", messageReplyDto);
    }

    User findById(Long id){ return  userRepository.findById(id).orElse(null);};
    User findByUserName(Long id){ return  userRepository.findById(id).orElse(null);};


}
