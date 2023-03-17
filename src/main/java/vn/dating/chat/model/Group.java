package vn.dating.chat.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import vn.dating.chat.model.audit.DateAudit;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
public class Group extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<GroupMember> members;

    @Enumerated(EnumType.STRING)
    private GroupType type;

    @Enumerated(EnumType.STRING)
    private GroupRandomType random;

    private String url;

    public void generateUrl(){
        String inputString = "group";
        UUID uuid = UUID.nameUUIDFromBytes(inputString.getBytes());
        this.url = uuid.toString();
    }
}

