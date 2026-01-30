package se.jensen.erik.socialmediaproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    /**
     * Konstruktor för AuthController.
     * @param authenticationManager Manager för att hantera autentisering.
     * @param tokenService Tjänst för att generera JWT-tokens.
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
    public ResponseEntity<LoginResponseDTO> token(
            @RequestBody LoginRequestDTO loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        MyUserDetails details = (MyUserDetails) auth.getPrincipal();
        details.getId();

        String token = tokenService.generateToken(auth);

        return ResponseEntity.ok(new LoginResponseDTO(token, details.getId()));
    }



}
