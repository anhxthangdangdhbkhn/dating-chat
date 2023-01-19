package vn.dating.chat.configs;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class SubscribeListener implements ApplicationListener<SessionSubscribeEvent> {
    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        System.out.println("正在订阅消息.");
        //我们可以从StompHeaderAccessor中拿到很多的信息，比如请求地址，请求类型等
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println(accessor.getDestination());
        ///topic/v1/chat
    }
}
