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
public class UserReceive extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant readAt;
    private boolean delete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user_id")
    private User userReceive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group groupChat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_chat_id")
    private Message receiveChat;
}
