package pl.morecraft.dev.settler.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.User;

import javax.inject.Inject;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Inject
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        String lowercaseLogin = login.toLowerCase();
        User userFromDatabase = userRepository.findOneByLogin(login);

        if (userFromDatabase == null)
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
//        else if (userFromDatabase.getStatus().equals(UserStatus.DIS))
//            throw new UserBlockedException("User " + lowercaseLogin + " was blocked");
//        else if (userFromDatabase.getStatus().equals(UserStatus.HNG))
//            throw new UserSuspendedException("User " + lowercaseLogin + " was suspended");
//        else if (!(userFromDatabase.getStatus().equals(UserStatus.ACT) || userFromDatabase.getStatus().equals(UserStatus.TMP)))
//            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
//        else if (userFromDatabase.getAccountExpireDate() != null &&
//                userFromDatabase.getStatus().equals(UserStatus.TMP) &&
//                userFromDatabase.getAccountExpireDate().isBefore(LocalDate.now()))
//            throw new AccountExpiredException("Account of user " + lowercaseLogin + " expired");


        return new UserDetailsWrapper(userFromDatabase);

    }
}
