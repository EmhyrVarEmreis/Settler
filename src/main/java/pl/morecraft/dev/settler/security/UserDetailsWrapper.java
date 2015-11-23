package pl.morecraft.dev.settler.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.morecraft.dev.settler.domain.User;

import java.util.Collection;
import java.util.Collections;

/**
 * Provide UserDetails compatible adapter to User data.
 */
public class UserDetailsWrapper implements UserDetails {

    private User user;

    public UserDetailsWrapper(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return !user.getStatus().equals(UserStatus.DIS);
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
        //return user.getStatus().equals(UserStatus.ACT) || user.getStatus().equals(UserStatus.TMP);
    }

}
