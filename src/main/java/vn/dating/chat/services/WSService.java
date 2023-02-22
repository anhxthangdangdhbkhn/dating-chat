package vn.dating.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.messages.socket.MessagePrivateDto;


@Service
public class WSService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationService notificationService;

    public void notifyFrontEnd(MessagePrivateDto messageReplyDto){
        notificationService.sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/messages", messageReplyDto);
    }

    public void notifyUser(MessagePrivateDto messageReplyDto){
        notificationService.sendPrivateNotification(messageReplyDto);
        messagingTemplate.convertAndSendToUser(messageReplyDto.getRecipientId(),"topic/private-messages", messageReplyDto);
    }
}
