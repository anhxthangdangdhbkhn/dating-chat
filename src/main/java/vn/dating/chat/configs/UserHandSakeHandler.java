package vn.dating.chat.configs;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class UserHandSakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        String randomId = UUID.randomUUID().toString();

        HttpHeaders headers = request.getHeaders();

        log.info("Customer header {}",headers.toString());

        log.info("User with id {}",randomId);
        return new UserPrincipal(randomId);
    }


}
