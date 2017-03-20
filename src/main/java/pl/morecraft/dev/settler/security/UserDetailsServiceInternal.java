package pl.morecraft.dev.settler.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.service.social.FacebookService;
import pl.morecraft.dev.settler.web.dto.LoginFbDTO;

@Component("userDetailsServiceInternal")
public class UserDetailsServiceInternal {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceInternal.class);

    private final UserRepository userRepository;
    private final FacebookService facebookService;

    @Autowired
    public UserDetailsServiceInternal(UserRepository userRepository, FacebookService facebookService) {
        this.userRepository = userRepository;
        this.facebookService = facebookService;
    }

    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        User userFromDatabase = userRepository.findOneByLogin(login);
        checkUser(userFromDatabase, login);
        return new UserDetailsWrapper(userFromDatabase);
    }

    @Transactional
    public UserDetails loadUserByFbId(final LoginFbDTO credentials) {
        Long id = facebookService.authTokenGetUserId(credentials.getKey());
        User userFromDatabase = userRepository.findOneByFbId(id);
        checkUser(userFromDatabase, String.valueOf(id));
        return new UserDetailsWrapper(userFromDatabase);
    }

    private void checkUser(User userFromDatabase, String login) {
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        }
    }

}
