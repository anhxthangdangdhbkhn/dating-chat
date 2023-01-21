package vn.dating.chat.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import vn.dating.chat.dto.MessageNewDto;
import vn.dating.chat.dto.OutputMessage;
import vn.dating.chat.dto.SessionIdMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WSConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private MyHandshakeInterceptor handshakeInterceptor;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chat");
//        registry.addEndpoint("/chat").withSockJS();
//                registry.addEndpoint("/chat");
//        registry.addEndpoint("/chat").withSockJS();

//        registry.addEndpoint("/ws")
//                .addInterceptors(new MyHandshakeInterceptor())
//        .setAllowedOrigins("http://localhost:5500","http://localhost:3000").
//                withSockJS();

        registry.addEndpoint("/ws")
   .withSockJS();
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        log.info("sessionId {}",sessionId);

        // sessionId is the connection key
    }







}