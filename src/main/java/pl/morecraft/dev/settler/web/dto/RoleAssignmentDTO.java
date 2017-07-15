package pl.morecraft.dev.settler.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAssignmentDTO {

    private String user;
    private String userId;
    private String roleId;
    private String roleName;
    private String target;
    private String targetId;

}
