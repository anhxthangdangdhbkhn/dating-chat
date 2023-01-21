package vn.dating.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dating.chat.model.Message;
import vn.dating.chat.repositories.MessageRepository;

@Service
public class MessageService  {

    @Autowired
    private MessageRepository messageRepository;

    public Message save(Message message){return  messageRepository.save(message);}
}
