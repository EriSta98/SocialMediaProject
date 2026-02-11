package se.jensen.erik.socialmediaproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import se.jensen.erik.socialmediaproject.dto.LoginRequestDTO;
import se.jensen.erik.socialmediaproject.dto.LoginResponseDTO;
import se.jensen.erik.socialmediaproject.security.MyUserDetails;
import se.jensen.erik.socialmediaproject.service.TokenService;

/**
 * Controller för hantering av autentisering och utfärdande av tokens.
 */
@RestController
@RequestMapping("/request-token")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    /**
     * Konstruktor för AuthController.
     * @param authenticationManager Manager för att hantera autentisering.
     * @param tokenService genererar JWT-tokens.
     */
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    /**
     * Autentiserar en användare och returnerar en JWT-token.
     * @param loginRequest Inloggningsuppgifter (användarnamn och lösenord).
     * @return En ResponseEntity innehållande token och användar-ID.
     */
    @PostMapping
    public ResponseEntity<?> token(
            @RequestBody LoginRequestDTO loginRequest) {

        try {
            logger.info("[DEBUG_LOG] Attempting authentication for user: {}", loginRequest.username());
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            MyUserDetails details = (MyUserDetails) auth.getPrincipal();

            String token = tokenService.generateToken(auth);

            logger.info("[DEBUG_LOG] Authentication successful for user: {}", loginRequest.username());
            return ResponseEntity.ok(new LoginResponseDTO(token, details.getId()));
        } catch (AuthenticationException e) {
            logger.warn("[DEBUG_LOG] Authentication failed for user: {}. Reason: {}", loginRequest.username(), e.getMessage());
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }



}
