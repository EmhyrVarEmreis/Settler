package pl.morecraft.dev.settler.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.security.UserDetailsWrapper;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.security.xauth.TokenProvider;
import pl.morecraft.dev.settler.web.dto.AuthenticationDTO;
import pl.morecraft.dev.settler.web.dto.LoginDTO;
import pl.morecraft.dev.settler.web.dto.UserDTO;

import java.util.Collections;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;

    @Autowired
    public AccountResource(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }


//    @Inject
//    private UserRepository userRepository;
//
//    @Inject
//    private UserService userService;
//
//    @Inject
//    private MailService mailService;

//    /**
//     * POST  /register -> register the user.
//     */
//    @RequestMapping(value = "/register",
//            method = RequestMethod.POST,
//            produces = MediaType.TEXT_PLAIN_VALUE)
//    @Timed
//    public ResponseEntity<?> registerAccount(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
//        return userRepository.findOneByLogin(userDTO.getLogin())
//            .map(user -> new ResponseEntity<>("login already in use", HttpStatus.BAD_REQUEST))
//            .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
//                .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
//                .orElseGet(() -> {
//                    User user = userService.createUserInformation(userDTO.getLogin(), userDTO.getPassword(),
//                    userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail().toLowerCase(),
//                    userDTO.getLangKey());
//                    String baseUrl = request.getScheme() + // "http"
//                    "://" +                                // "://"
//                    request.getServerName() +              // "myhost"
//                    ":" +                                  // ":"
//                    request.getServerPort();               // "80"
//
//                    mailService.sendActivationEmail(user, baseUrl);
//                    return new ResponseEntity<>(HttpStatus.CREATED);
//                })
//        );
//    }
//    /**
//     * GET  /activate -> activate the registered user.
//     */
//    @RequestMapping(value = "/activate",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
//        return Optional.ofNullable(userService.activateRegistration(key))
//            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
//            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//    }
//
//    /**
//     * GET  /authenticate -> check if the user is authenticated, and return its login.
//     */
//    @RequestMapping(value = "/authenticate",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public String isAuthenticated(HttpServletRequest request) {
//        log.debug("REST request to check if the current user is authenticated");
//        return request.getRemoteUser();
//    }

    /**
     * GET  /account -> get the current user.
     */
    @RequestMapping(value = "/account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
    public UserDTO getAccount() {
        User user = Security.currentUser();
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                Collections.emptyList()
        );
    }

//    /**
//     * POST  /account -> update the current user information.
//     */
//    @RequestMapping(value = "/account",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<String> saveAccount(@RequestBody UserDTO userDTO) {
//        return userRepository
//            .findOneByLogin(userDTO.getLogin())
//            .filter(u -> u.getLogin().equals(SecurityUtils.getCurrentLogin()))
//            .map(u -> {
//                userService.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail());
//                return new ResponseEntity<String>(HttpStatus.OK);
//            })
//            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//    }
//
//    /**
//     * POST  /change_password -> changes the current user's password
//     */
//    @RequestMapping(value = "/account/change_password",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<?> changePassword(@RequestBody String password) {
//        if (StringUtils.isEmpty(password)) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        userService.changePassword(password);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    @Timed
    public AuthenticationDTO authorize(@RequestBody LoginDTO credentials) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsWrapper details = (UserDetailsWrapper) this.userDetailsService.loadUserByUsername(credentials.getLogin());
        //return new AuthenticationDTO(tokenProvider.createToken(details), details.getUser().getPasswordExpireDate());
        return new AuthenticationDTO(tokenProvider.createToken(details), null);
    }
}
