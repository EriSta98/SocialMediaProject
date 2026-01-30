package se.jensen.erik.socialmediaproject.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.jensen.erik.socialmediaproject.model.User;
import se.jensen.erik.socialmediaproject.repository.UserRepository;

/**
 * Implementationsklass av UserDetailsService för att ladda användaruppgifter vid inloggning.
 */
@Service
public class DetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Konstruktor för DetailsService.
     * @param userRepository Repository för användare.
     */
    public DetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Laddar en användare baserat på användarnamn för Spring Security.
     * @param username Användarnamnet.
     * @return UserDetails-objekt för den hittade användaren.
     * @throws UsernameNotFoundException Om användaren inte finns.
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));


        return new MyUserDetails(user);
    }

}
