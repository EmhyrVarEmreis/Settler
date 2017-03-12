package pl.morecraft.dev.settler.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithValueDTO {

    private Long userId;
    private String user;
    private Double value;

}
