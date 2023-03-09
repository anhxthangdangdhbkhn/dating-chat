package vn.dating.chat.model;

import lombok.*;
import vn.dating.chat.model.audit.DateAudit;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Token extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userToken;

    private String accessToken;
    private String refreshToken;

    private Instant accessExpiry;
    private Instant refreshExpiry;
    private String deviceType;
    private String deviceId;
    private String deviceOS;
    private String location;

}