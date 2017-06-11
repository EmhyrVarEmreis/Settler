package pl.morecraft.dev.settler.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.security.UserDetailsServiceInternal;
import pl.morecraft.dev.settler.security.UserDetailsWrapper;
import pl.morecraft.dev.settler.security.exception.AuthorizationMethodNotImplemented;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.security.xauth.TokenProvider;
import pl.morecraft.dev.settler.service.PermissionService;
import pl.morecraft.dev.settler.web.dto.AuthenticationDTO;
import pl.morecraft.dev.settler.web.dto.LoginDTO;
import pl.morecraft.dev.settler.web.dto.LoginFbDTO;
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
    private final UserDetailsServiceInternal userDetailsService;
    private final TokenProvider tokenProvider;
    private final PermissionService permissionService;

    @Autowired
    public AccountResource(AuthenticationManager authenticationManager, UserDetailsServiceInternal userDetailsService, TokenProvider tokenProvider, PermissionService permissionService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getAccount() {
        User user = Security.currentUser();
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                permissionService.getShortSummary(),
                Collections.emptyList()
        );
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public AuthenticationDTO authorize(@RequestBody LoginDTO credentials) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsWrapper details = (UserDetailsWrapper) this.userDetailsService.loadUserByUsername(credentials.getLogin());
        return new AuthenticationDTO(tokenProvider.createToken(details), null);
    }

    @RequestMapping(value = "/authenticate/fb", method = RequestMethod.POST)
    public AuthenticationDTO authorizeFb(@RequestBody LoginFbDTO credentials) {
        UserDetailsWrapper details = (UserDetailsWrapper) this.userDetailsService.loadUserByFbId(credentials);
        return new AuthenticationDTO(tokenProvider.createToken(details), null);
    }

    @RequestMapping(value = "/authenticate/google", method = RequestMethod.POST)
    public AuthenticationDTO authorizeGoogle() {
        throw new AuthorizationMethodNotImplemented("Google authentication is not implemented yet");
    }

}
