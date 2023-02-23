package vn.dating.chat.model;

import lombok.*;
import vn.dating.chat.model.audit.DateAudit;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
public class GroupMember extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
