package pl.morecraft.dev.settler.domain;

import pl.morecraft.dev.settler.domain.dictionaries.UserStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prv_user")
//@Audited
public class User extends PrivilegeObject {

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    private String email;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDate created;
    private LocalDate passwordExpireDate;
    private LocalDate accountExpireDate;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getPasswordExpireDate() {
        return passwordExpireDate;
    }

    public void setPasswordExpireDate(LocalDate passwordExpireDate) {
        this.passwordExpireDate = passwordExpireDate;
    }

    public LocalDate getAccountExpireDate() {
        return accountExpireDate;
    }

    public void setAccountExpireDate(LocalDate accountExpireDate) {
        this.accountExpireDate = accountExpireDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + "\'," +
                "login='" + login + '\'' +
                '}';
    }
}
