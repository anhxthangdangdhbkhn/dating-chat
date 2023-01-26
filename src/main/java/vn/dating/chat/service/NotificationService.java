package vn.dating.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.MessageReplyDto;
import vn.dating.chat.dto.ResponseMessage;

@Service
public class NotificationService {

    @Autowired
    private  SimpMessagingTemplate messagingTemplate;


    public void sendGlobalNotification() {
        ResponseMessage message = new ResponseMessage("Global Notification");

        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification( MessageReplyDto messageReplyDto) {
        messagingTemplate.convertAndSendToUser(messageReplyDto.getRecipientId(),"/topic/private-notifications", messageReplyDto);
    }
}
