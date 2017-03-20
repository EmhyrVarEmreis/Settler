package pl.morecraft.dev.settler.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialDTO {

    private String fbId;
    private String fbToken;
    private String googleId;

}
