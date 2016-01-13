package pl.morecraft.dev.settler.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.service.UserService;
import pl.morecraft.dev.settler.web.dto.UserDTO;
import pl.morecraft.dev.settler.web.dto.UserListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Inject
    private UserService userService;

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public UserDTO get(@RequestParam(value = "id", required = true) Long userId) {
        return userService.get(userId);
    }

    @RequestMapping("/list")
    public ListPage<UserListDTO> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(value = "limit", required = false, defaultValue = "25") Integer limit,
                                          @RequestParam(value = "sortBy", required = false, defaultValue = "-username") String sortBy,
                                          @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return userService.get(page, limit, sortBy, filters);
    }

}
