package vn.dating.chat.model;

import lombok.*;
import vn.dating.chat.model.Message;
import vn.dating.chat.model.audit.DateAudit;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@Entity
public class  User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String chat;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Message> sender;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Message> recipient;
}
