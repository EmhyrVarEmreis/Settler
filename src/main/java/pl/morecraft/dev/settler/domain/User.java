package pl.morecraft.dev.settler.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.domain.dictionaries.UserStatus;

import javax.persistence.*;

@Entity
@Table(name = "prv_user")
@Getter
@Setter
@NoArgsConstructor
public class User extends PrivilegeObject {

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String email;
    private String firstName;
    private String lastName;

    private LocalDateTime created;
    private LocalDate passwordExpireDate;
    private LocalDate accountExpireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar")
    private FileObject avatar;

    @Column(name = "avatar", insertable = false, updatable = false)
    private Long avatarId;

    private Long fbId;

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + "\'," +
                "login='" + login + '\'' +
                '}';
    }

}
