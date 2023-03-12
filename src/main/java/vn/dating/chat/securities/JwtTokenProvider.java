package vn.dating.chat.securities;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;
import vn.dating.chat.dto.auth.AuthDto;
import vn.dating.chat.mapper.AuthMapper;
import vn.dating.chat.model.Token;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
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



    public AuthDto generateConnect(HttpServletRequest request, User currentUser, Authentication authentication){

        String userAgent = request.getHeader("User-Agent");
        UserAgent parsedUserAgent = UserAgent.parseUserAgentString(userAgent);
        Browser browser = parsedUserAgent.getBrowser();
        if(!Objects.isNull(browser)){
//            String browserName = browser.getName();
//            String browserVersion = parsedUserAgent.getBrowserVersion().getVersion();
        }

        DeviceType deviceType = parsedUserAgent.getOperatingSystem().getDeviceType();
        String ipAddress = request.getRemoteAddr();

        String deviceOs ="";
        Parser parser = null;

        try {
            parser = new Parser();
            Client client = parser.parse(userAgent);
            deviceOs = client.os.toString();
//            log.info(client.os.toString());
//            log.info(client.device.toString());
//            log.info(client.userAgent.toString());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Date now = new Date();
        Date expiryToken = new Date(now.getTime() + jwtExpirationInMs);
        Date expiryRefresh = new Date(now.getTime() + jwtExpirationInMsReToken);

        String accessToken = generateToken(authentication,expiryToken);
        String refreshToken = generateToken(authentication,expiryRefresh);


        Token token = new Token();

        token.setUserToken(currentUser);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setIp(ipAddress);
        token.setDeviceType(deviceType.getName());
        token.setDeviceOS(deviceOs);

        token.setAccessExpiry(expiryToken.toInstant());
        token.setRefreshExpiry(expiryRefresh.toInstant());

        token = tokenRepository.save(token);
        if(token==null) return null;

        return AuthMapper.userToAuth(token);
    }

    public AuthDto updateRefreshToken( Authentication authentication, Token currentToken){

        Date now = new Date();
        Date expiryToken = new Date(now.getTime() + jwtExpirationInMs);
        Date expiryRefresh = new Date(now.getTime() + jwtExpirationInMsReToken);

        String accessToken = generateToken(authentication,expiryToken);
        String refreshToken = generateToken(authentication,expiryRefresh);

        User currentUser = currentToken.getUserToken();

        currentToken.setUserToken(currentUser);
        currentToken.setAccessToken(accessToken);
        currentToken.setRefreshToken(refreshToken);

        currentToken.setAccessExpiry(expiryToken.toInstant());
        currentToken.setRefreshExpiry(expiryRefresh.toInstant());

        currentToken = tokenRepository.saveAndFlush(currentToken);
        if(currentToken==null){
            logger.info("cannot update");
            return null;
        }else {
            logger.info("update is ok");

        }

        return AuthMapper.userToAuth(currentToken);
    }



    public String generateToken(Authentication authentication,Date expiryToken) {

        LocalDateTime localDateTime = LocalDateTime.now();
//        log.info(localDateTime.atZone(ZoneId.systemDefault()).toString());
        Instant japanTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date now  = Date.from(japanTime);
        log.info(now.toInstant().toString());

        log.info(expiryToken.toInstant().toString());

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken =  Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(now)
                .setExpiration(expiryToken).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return accessToken;
    }


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
