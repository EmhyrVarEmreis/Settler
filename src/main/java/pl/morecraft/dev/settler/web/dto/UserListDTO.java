package pl.morecraft.dev.settler.web.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.morecraft.dev.settler.web.utils.JsonDateSerializer;

import java.time.LocalDate;

public class UserListDTO {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;

    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate created;

    public UserListDTO() {
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
}
