package vn.dating.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.messages.MessagePrivateDto;
import vn.dating.chat.dto.messages.ResponsePrivateMessage;

@Service
public class NotificationService {

    @Autowired
    private  SimpMessagingTemplate messagingTemplate;


    public void sendGlobalNotification() {
        ResponsePrivateMessage message = new ResponsePrivateMessage("Global Notification");

        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification( MessagePrivateDto messageReplyDto) {
        messagingTemplate.convertAndSendToUser(messageReplyDto.getRecipientId(),"/topic/private-notifications", messageReplyDto);
    }
}
