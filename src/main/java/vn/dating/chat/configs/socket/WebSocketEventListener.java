package vn.dating.chat.configs.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import vn.dating.chat.utils.ConnectedUsers;


@Component
@Slf4j
public class WebSocketEventListener  {

    @EventListener
    public void onApplicationEvent(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = event.getUser().getName();
        String session = headerAccessor.getSessionId();

        ConnectedUsers.addUser(userId, session);
        log.info("User online {}",userId);
        log.info("User online {}",session);
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = event.getUser().getName();
        String session = headerAccessor.getSessionId();

        ConnectedUsers.removeUser(userId, session);
        log.info("User offline {}",userId);
        log.info("User offline {}",session);
    }


}

