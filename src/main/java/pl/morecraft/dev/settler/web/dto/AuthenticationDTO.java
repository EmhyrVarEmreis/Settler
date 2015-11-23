package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.morecraft.dev.settler.web.utils.JsonDateDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonDateSerializer;

import java.time.LocalDate;

/**
 * Contains authentication result
 */
public class AuthenticationDTO {
    private String token;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate passwordExpireDate;

    public AuthenticationDTO(String token, LocalDate passwordExpireDate) {
        this.token = token;
        this.passwordExpireDate = passwordExpireDate;
    }

    public String getToken() {
        return token;
    }

    public LocalDate getPasswordExpireDate() {
        return passwordExpireDate;
    }
}
