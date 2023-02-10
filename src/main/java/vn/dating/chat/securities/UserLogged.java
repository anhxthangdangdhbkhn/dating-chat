package vn.dating.chat.securities;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Getter
@Setter
@Slf4j
public class UserLogged {

    Authentication auth;

    private long id;

    private String email;




    public UserLogged() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        email = auth.getName();
    }
}
