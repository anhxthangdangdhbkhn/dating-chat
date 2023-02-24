package vn.dating.chat.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import vn.dating.chat.model.audit.DateAudit;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private GroupType type;

    private String url;
}

