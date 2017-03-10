package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateTimeDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateTimeSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProfileDTO {

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

    @JsonSerialize(using = JsonJodaLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateTimeDeserializer.class)
    private LocalDateTime created;

    private String status;

    private Long avatar;

}
