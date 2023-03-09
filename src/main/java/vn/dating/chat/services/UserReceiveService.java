package vn.dating.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.messages.api.ResultGroupMessage;
import vn.dating.chat.dto.messages.api.ResultMessage;
import vn.dating.chat.dto.messages.socket.MessagePrivateGroupOutputDto;
import vn.dating.chat.model.UserReceive;
import vn.dating.chat.repositories.UserReceiveRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserReceiveService {

    @Autowired
    private UserReceiveRepository userReceiveRepository;

    public UserReceive save(UserReceive userReceive){
        return userReceiveRepository.save(userReceive);
    }

    public Page<UserReceive> findByGroupChatId(Long groupId,int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        return  userReceiveRepository.findByGroupChatId(groupId,pageable);
    }

    public ResultGroupMessage findMessageByGroupId(Long groupId,int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<UserReceive> receivePage =  userReceiveRepository.findByGroupChatId(groupId,pageable);

        ResultGroupMessage resultGroupMessage = new ResultGroupMessage();
        receivePage.getContent().forEach(m->{
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setId(m.getReceiveChat().getId());
            resultMessage.setContent(m.getReceiveChat().getContent());
            resultMessage.setSenderEmail(m.getUserReceive().getEmail());
            resultMessage.setSenderName(m.getUserReceive().getUsername());

            resultGroupMessage.setGroupId(m.getGroupChat().getId());
            resultGroupMessage.addMessage(resultMessage);

        });

        return resultGroupMessage;
    }
}
