package vn.dating.chat.dto.auth;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private Long id;
    private String accessToken;
    private String refreshToken;

    private Instant accessExpiry;
    private Instant refreshExpiry;
    private String deviceType;
    private String deviceId;
    private String deviceOS;
    private String location;
}
