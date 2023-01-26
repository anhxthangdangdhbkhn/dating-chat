package vn.dating.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.MessageReplyDto;


@Service
public class WSService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationService notificationService;

    public void notifyFrontEnd(MessageReplyDto messageReplyDto){
        notificationService.sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/messages", messageReplyDto);
    }

    public void notifyUser(MessageReplyDto messageReplyDto){
        notificationService.sendPrivateNotification(messageReplyDto);
        messagingTemplate.convertAndSendToUser(messageReplyDto.getRecipientId(),"topic/private-messages", messageReplyDto);
    }
}
