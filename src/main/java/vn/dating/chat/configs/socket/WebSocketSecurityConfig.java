package vn.dating.chat.configs.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        //messages.anyMessage().permitAll();
        messages

                .nullDestMatcher().permitAll()

                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()

                .simpDestMatchers("/ws/**").permitAll()

                .simpSubscribeDestMatchers("/ws/**","/user/**", "/topic/**","/queue/**").permitAll()

                .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).permitAll()

                .anyMessage().permitAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}
