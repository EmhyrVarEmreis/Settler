package pl.morecraft.dev.settler.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.morecraft.dev.settler.domain.dictionaries.internal.SocialEnum;
import pl.morecraft.dev.settler.service.UserService;
import pl.morecraft.dev.settler.web.dto.*;
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

    @GetMapping(value = "/details")
    public ResponseEntity<UserDTO> get(@RequestParam(value = "id", required = true) Long userId) {
        return userService.get(userId);
    }

    @PostMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileDTO> getProfile() {
        return userService.getProfile();
    }

    @PostMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDTO) {
        return userService.saveProfile(profileDTO);
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

    @GetMapping(value = "/avatar")
    public ResponseEntity<AvatarDTO> getAvatar(
            @RequestParam(value = "id", defaultValue = "-13", required = false) Long id,
            @RequestParam(value = "login", defaultValue = "", required = false) String login) throws IOException {
        return userService.getAvatar(id, login);
    }

    @GetMapping(value = "/values")
    public ResponseEntity<List<UserWithValueDTO>> getUsersWithValue(@RequestParam(value = "count", required = false, defaultValue = "-13") Long userId) {
        return userService.getUsersWithValue(userId);
    }

    @GetMapping(value = "/social")
    public ResponseEntity<UserSocialDTO> getUserSocial(UserIdDTO user) {
        return userService.getUserSocial(userService.getUser(user));
    }

    @DeleteMapping(value = "/social/fb")
    public ResponseEntity disintegrationFb(UserIdDTO user) {
        return userService.removeSocialIntegration(userService.getUser(user), SocialEnum.FACEBOOK);
    }

    @PostMapping(value = "/social/fb")
    public ResponseEntity integrationFbPost(@RequestBody IntegrationContext context) {
        return userService.addSocialIntegration(userService.getUser(context.getUser()), SocialEnum.FACEBOOK, context.getUserSocial());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IntegrationContext {
        private UserIdDTO user;
        private UserSocialDTO userSocial;
    }

}
