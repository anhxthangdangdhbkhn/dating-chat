package vn.dating.chat.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;


@Slf4j
@Component
public class ConnectListener implements ApplicationListener<SessionConnectEvent> {


    @Override
    public void onApplicationEvent(SessionConnectEvent event) {

        System.out.println("ConnectListener");

    }


//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectEvent sessionConnectEvent) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionConnectEvent.getMessage());
//        log.debug("connect, sessionId = {}", headerAccessor.getSessionId());
//    }
//
//    @EventListener
//    public void handleWebSocketConnectedListener(SessionConnectedEvent sessionConnectedEvent) {
//        log.debug("connected ");
//    }
//
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        System.out.println("Disconnect");
//        System.out.println(event.getSessionId());
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if(username != null) {
//
//            WebSocketChatMessage chatMessage = new WebSocketChatMessage();
//            chatMessage.setType("Leave");
//            chatMessage.setSender(username);
//
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
//    }


}
