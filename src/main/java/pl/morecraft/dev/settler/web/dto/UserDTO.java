package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Email;
import pl.morecraft.dev.settler.domain.dictionaries.UserStatus;
import pl.morecraft.dev.settler.web.utils.JsonDateDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonDateSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class UserDTO {

    @NotNull
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotNull
    @Size(min = 1, max = 32)
    private String login;

    @NotNull
    @Size(min = 6, max = 128)
    private String password;

    @Pattern(regexp = "^[a-zA-Z]*$")
    @Size(max = 50)
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]*$")
    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 6, max = 128)
    private String email;

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDate created;

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDate passwordExpireDate;

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDate accountExpireDate;

    private UserStatus status;

    private List<RoleAssignmentDTO> roleAssignments;

    public UserDTO() {
    }

    public UserDTO(String login, String password, String firstName, String lastName, String email, List<RoleAssignmentDTO> roleAssignments) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleAssignments = roleAssignments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<RoleAssignmentDTO> getRoleAssignments() {
        return roleAssignments;
    }

    public void setRoleAssignments(List<RoleAssignmentDTO> roleAssignments) {
        this.roleAssignments = roleAssignments;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roleAssignments +
                '}';
    }

}
