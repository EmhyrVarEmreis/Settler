package pl.morecraft.dev.settler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.morecraft.dev.settler.service.UserService;
import pl.morecraft.dev.settler.web.dto.AvatarDTO;
import pl.morecraft.dev.settler.web.dto.UserDTO;
import pl.morecraft.dev.settler.web.dto.UserListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            value = "/details",
            method = RequestMethod.GET
    )
    public ResponseEntity<UserDTO> get(@RequestParam(value = "id", required = true) Long userId) {
        return userService.get(userId);
    }

    @RequestMapping(
            value = "/details",
            method = {RequestMethod.PUT, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @RequestMapping("/list")
    public ListPage<UserListDTO> getPaged(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(value = "limit", required = false, defaultValue = "25") Integer limit,
                                          @RequestParam(value = "sortBy", required = false, defaultValue = "-login") String sortBy,
                                          @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return userService.get(page, limit, sortBy, filters);
    }

    @RequestMapping("/search/simple")
    public ResponseEntity<List<UserListDTO>> getPaged(@RequestParam(value = "limit", required = false, defaultValue = "25") Integer limit,
                                                      @RequestParam(value = "string", required = false, defaultValue = "") String string) {
        return userService.searchSimple(limit, string);
    }

    @RequestMapping(
            value = "/avatar",
            method = RequestMethod.GET
    )
    public ResponseEntity<AvatarDTO> getExcelFile(
            @RequestParam(value = "id", defaultValue = "-13", required = false) Long id,
            @RequestParam(value = "login", defaultValue = "", required = false) String login) throws IOException {
        return userService.getAvatar(id, login);
    }

}
