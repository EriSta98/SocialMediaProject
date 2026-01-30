package se.jensen.erik.socialmediaproject.dto;

/**
 * Data Transfer Object för inloggningssvar.
 * @param token JWT-token.
 * @param userId Användarens ID.
 */
public record LoginResponseDTO(String token, Long userId) {


}