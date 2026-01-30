package se.jensen.erik.socialmediaproject.security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.jensen.erik.socialmediaproject.model.User;
import java.util.Collection;
import java.util.List;

/**
 * Implementationsklass av UserDetails som bryggar vår User-modell till Spring Security.
 */
public class MyUserDetails implements UserDetails {

    private final User user;

    /**
     * Konstruktor för MyUserDetails.
     * @param user Användarentiteten från vår domänmodell.
     */
    public MyUserDetails(User user) {
        this.user = user;
    }

    /**
     * Hämtar användarnamn.
     * @return Användarnamnet.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Hämtar lösenordet.
     * @return Det krypterade lösenordet.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Hämtar användarens rättigheter (roller).
     * @return En samling av GrantedAuthority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    // "USER" eller "ADMIN"
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    /**
     * Kontrollerar om kontot inte har gått ut.
     * @return sant (för närvarande hårdkodat).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // kan byggas ut senare
    }

    /**
     * Kontrollerar om kontot inte är låst.
     * @return sant (för närvarande hårdkodat).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // kan byggas ut senare
    }

    /**
     * Kontrollerar om autentiseringsuppgifterna inte har gått ut.
     * @return sant (för närvarande hårdkodat).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // kan byggas ut senare
    }

    /**
     * Kontrollerar om kontot är aktiverat.
     * @return sant (för närvarande hårdkodat).
     */
    @Override
    public boolean isEnabled() {
        return true; // kan byggas ut senare
    }

    /**
     * Hämtar den underliggande domänanvändaren.
     * @return User-entiteten.
     */
    public User getDomainUser() {
        return user;
    }

    /**
     * Hämtar användarens ID.
     * @return ID.
     */
    public Long getId() {
        return user.getId();
    }

}
