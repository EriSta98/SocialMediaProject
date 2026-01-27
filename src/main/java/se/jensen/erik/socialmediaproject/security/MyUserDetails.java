package se.jensen.erik.socialmediaproject.security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.jensen.erik.socialmediaproject.model.User;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    // "USER" eller "ADMIN"
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // kan byggas ut senare
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // kan byggas ut senare
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // kan byggas ut senare
    }

    @Override
    public boolean isEnabled() {
        return true; // kan byggas ut senare
    }

    public User getDomainUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

}
