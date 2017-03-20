package pl.morecraft.dev.settler.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    private final UserDetailsServiceInternal userDetailsServiceInternal;

    @Autowired
    public UserDetailsService(UserDetailsServiceInternal userDetailsServiceInternal) {
        this.userDetailsServiceInternal = userDetailsServiceInternal;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        return userDetailsServiceInternal.loadUserByUsername(login);
    }

}
