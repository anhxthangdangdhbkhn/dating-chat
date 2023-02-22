package vn.dating.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.messages.socket.MessagePrivateDto;
import vn.dating.chat.dto.messages.socket.ConfirmPrivateMessage;

@Service
public class NotificationService {

    @Autowired
    private  SimpMessagingTemplate messagingTemplate;


    public void sendGlobalNotification() {
        ConfirmPrivateMessage message = new ConfirmPrivateMessage("Global Notification");

        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification( MessagePrivateDto messageReplyDto) {
        messagingTemplate.convertAndSendToUser(messageReplyDto.getRecipientId(),"/topic/private-notifications", messageReplyDto);
    }
}
