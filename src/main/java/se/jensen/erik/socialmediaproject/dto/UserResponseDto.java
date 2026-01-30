package se.jensen.erik.socialmediaproject.dto;

import se.jensen.erik.socialmediaproject.model.User;

/**
 * Data Transfer Object för svar som innehåller användarinformation.
 * @param id Användarens ID.
 * @param username Användarnamn.
 * @param role Roll.
 * @param displayName Visningsnamn.
 * @param profileImagePath Sökväg till profilbild.
 * @param email E-postadress.
 * @param bio Biografi.
 */
public record UserResponseDto(
    Long id,
    String username,
    String role,
    String displayName,
    String profileImagePath,
    String email,
    String bio) {


    /**
     * Konverterar en User-entitet till UserResponseDto.
     * @param user Användarentiteten som ska konverteras.
     * @return En ny UserResponseDto.
     */
    public static UserResponseDto fromEntity(User user) {

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getDisplayName(),
                user.getBio(),
                user.getProfileImagePath()
        );
    }


}



