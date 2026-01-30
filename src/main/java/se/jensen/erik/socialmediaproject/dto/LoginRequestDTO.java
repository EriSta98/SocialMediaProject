package se.jensen.erik.socialmediaproject.dto;

/**
 * Data Transfer Object för inloggningsförfrågningar.
 * @param username Användarnamn.
 * @param password Lösenord.
 */
public record LoginRequestDTO(String username, String password) {



}