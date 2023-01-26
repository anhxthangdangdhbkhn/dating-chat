package vn.dating.chat.configs.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicConfiguration  extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user01 = User.withUsername("thang")
                .password(passwordEncoder.encode("thang"))
                .roles("USER")
                .build();
        UserDetails user02 = User.withUsername("dung")
                .password(passwordEncoder.encode("dung"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user01,user02, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
                messages

                .nullDestMatcher().permitAll()

                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()

                .simpDestMatchers("/ws/**").permitAll()

                .simpSubscribeDestMatchers("/ws/**","/user/**", "/topic/**","/queue/**").permitAll()

                .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).permitAll()

                .anyMessage().permitAll();

//        messages
//
//                .nullDestMatcher().authenticated()
//
//                .simpSubscribeDestMatchers("/user/queue/errors").authenticated()
//
//                .simpDestMatchers("/ws/**").authenticated()
//
//                .simpSubscribeDestMatchers("/ws/**","/user/**", "/topic/**","/queue/**").authenticated()
//
//                .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).authenticated()
//
//                .anyMessage().authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}