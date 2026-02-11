package se.jensen.erik.socialmediaproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DetailsService.class);
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

        logger.info("[DEBUG_LOG] Attempting to load user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("[DEBUG_LOG] User not found: {}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });


        logger.info("[DEBUG_LOG] User found: {}, role: {}, encoded password: {}", username, user.getRole(), user.getPassword());
        return new MyUserDetails(user);
    }

}
