package vn.dating.chat.securities;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import vn.dating.chat.controllers.AuthController;
import vn.dating.chat.dto.auth.AuthDto;
import vn.dating.chat.mapper.AuthMapper;
import vn.dating.chat.model.Token;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.TokenRepository;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Value("${app.jwtExpirationInMsReToken}")
    private int jwtExpirationInMsReToken;

    @Autowired
    private TokenRepository tokenRepository;



    public AuthDto generateConnect(User currentUser, Authentication authentication){

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryToken = new Date(now.getTime() + jwtExpirationInMs);
        Date expiryRefresh = new Date(now.getTime() + jwtExpirationInMsReToken);

        String accessToken =  Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
                .setExpiration(expiryToken).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();


        String refreshToken = Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
                .setExpiration(expiryRefresh).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();


        Token token = new Token();
        token.setUserToken(currentUser);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        token.setAccessExpiry(expiryToken.toInstant());
        token.setRefreshExpiry(expiryRefresh.toInstant());

        token = tokenRepository.save(token);
        if(token==null) return null;



        return AuthMapper.userToAuth(token);
    }



//    public String generateToken(Authentication authentication) {
//
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
//
//        return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
//                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//    }
//
//    public String generateRefreshToken(Authentication authentication) {
//
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationInMsReToken);
//
//        return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
//                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//    }

    public Long getUserIdFromJWT(String token) {
        logger.info("getUserIdFromJWT");
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {

        List<Token> lists =  tokenRepository.findByAccessTokenOrRefreshToken(authToken,authToken);
        if (lists.size()==0) return false;


        try {
            logger.info("Token {}",authToken);
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }


        return false;
    }
}
