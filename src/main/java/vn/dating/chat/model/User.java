package vn.dating.chat.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import vn.dating.chat.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;


    @NotBlank
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    private String avatar;


    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private Instant createExpiry;
    private String createToken;
    private Instant resetExpiry;
    private String resetToken;

    public void newUser(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = false;
    }




    public User(Long id) {
        this.id = id;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Follower> followers;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Message> sender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<GroupMember> members;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Group> admin;

    @OneToMany(mappedBy = "userReceive", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserReceive> userReceive;

    @OneToMany(mappedBy = "userToken", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Token> tokens;
}
