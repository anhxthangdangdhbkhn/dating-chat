package vn.dating.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.dating.chat.dto.MessageDto;

@Service
public class WSService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationService notificationService;

    public void notifyFrontEnd(MessageDto messageDto){
        notificationService.sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/messages",messageDto);
    }

    public void notifyUser(String id, String message){
        notificationService.sendPrivateNotification(id);
        messagingTemplate.convertAndSendToUser(id,"topic/private-messages",message);
    }
}
