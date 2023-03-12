package vn.dating.chat.dto.auth;

import java.time.Instant;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String email;
    private String avatar;
    private Long tokenId;
    private Instant accessExpiry;
    private Instant refreshExpiry;

    private String tokenType = "Bearer";

//    private UserProfile user;

    public JwtAuthenticationResponse(String accessToken, String refreshToken,String avatar, UserProfile user) {
        this.email = user.getEmail();
        this.userId = user.getId();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.avatar = avatar;
    }

    public JwtAuthenticationResponse(AuthDto authDto, String avatar, UserProfile user) {
        this.email = user.getEmail();
        this.userId = user.getId();
        this.accessToken = authDto.getAccessToken();
        this.refreshToken = authDto.getRefreshToken();
        this.tokenId = authDto.getId();
        this.accessExpiry = authDto.getAccessExpiry();
        this.refreshExpiry = authDto.getRefreshExpiry();
        this.avatar = avatar;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public Instant getAccessExpiry() {
        return accessExpiry;
    }

    public void setAccessExpiry(Instant accessExpiry) {
        this.accessExpiry = accessExpiry;
    }

    public Instant getRefreshExpiry() {
        return refreshExpiry;
    }

    public void setRefreshExpiry(Instant refreshExpiry) {
        this.refreshExpiry = refreshExpiry;
    }
}
