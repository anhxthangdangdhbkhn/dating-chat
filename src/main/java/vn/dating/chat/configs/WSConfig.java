package vn.dating.chat.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import vn.dating.chat.dto.MessageNewDto;
import vn.dating.chat.dto.OutputMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WSConfig extends AbstractWebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat");
        registry.addEndpoint("/chat").withSockJS();
    }


    @Bean
    ConnectListener connectListener(){
        return new ConnectListener();
    }

    @Bean
    ConnectedListener connectedListener(){
        return new ConnectedListener();
    }

    @Bean
    DisconnectListener disconnectListener(){
        return new DisconnectListener();
    }

//    @Override
//    public void onApplicationEvent(BrokerAvailabilityEvent e) {
//        System.out.println(e.isBrokerAvailable());
//    }

    @Controller
    @RequestMapping("/")
    public class WSController {

        @MessageMapping("/chat")
        @SendTo("/topic/messages")
        public OutputMessage send(@Payload MessageNewDto message) throws Exception {
            System.out.println("chat");
            final String time = new SimpleDateFormat("HH:mm").format(new Date());
            return new OutputMessage(message.getFrom(), message.getText(), time);
        }
    }

}