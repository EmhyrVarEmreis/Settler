package pl.morecraft.dev.settler.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.service.PermissionService;
import pl.morecraft.dev.settler.web.dto.RoleAssignmentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    final private PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(
            value = "/role/assignment/target",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<RoleAssignmentDTO>> getRoleAssignmentsByTarget(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(
                permissionService.getRoleAssignmentsByPrivilegeObject(id),
                HttpStatus.OK
        );
    }

}
