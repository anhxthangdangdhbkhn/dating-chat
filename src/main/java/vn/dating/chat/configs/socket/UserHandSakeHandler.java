package vn.dating.chat.configs.socket;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class UserHandSakeHandler extends DefaultHandshakeHandler  {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {


//        HttpHeaders headers = request.getHeaders();
//        log.info("Customer header {}",attributes.size());
//       String name = request.getPrincipal().getName();
//        log.info("UserLogin {}",name);
//
//        attributes.forEach((k,d)->{
//            Object specificAttribute = attributes.get(k);
//            log.info("Specific attribute value: {}", specificAttribute);
//        });
//
//        log.info("Customer header {}",request.getHeaders().toString());
//        String randomId = UUID.randomUUID().toString();
        log.info("User with id {}", request.getPrincipal().getName());
        return new UserPrincipal( request.getPrincipal().getName());
    }


}
