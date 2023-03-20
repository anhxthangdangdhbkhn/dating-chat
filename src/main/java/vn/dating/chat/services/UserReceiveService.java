package vn.dating.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dating.chat.model.UserReceive;
import vn.dating.chat.repositories.MessageRepository;
import vn.dating.chat.repositories.UserReceiveRepository;


@Service
public class UserReceiveService {

    @Autowired
    private UserReceiveRepository userReceiveRepository;

    @Autowired
    private MessageRepository messageRepository;

    public UserReceive save(UserReceive userReceive){
        return userReceiveRepository.save(userReceive);
    }


}
