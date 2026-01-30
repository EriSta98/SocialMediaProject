package se.jensen.erik.socialmediaproject.dto;


import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object för att skapa eller uppdatera en användare.
 * @param username Användarnamn.
 * @param password Lösenord.
 * @param role Roll (t.ex. USER eller ADMIN).
 * @param displayName Visningsnamn.
 * @param profileImagePath Sökväg till profilbild.
 * @param email E-postadress.
 * @param bio Kort biografi.
 */
public record UserRequestDto(
        @NotBlank(message = "Namn får inte vara tom.")
        @Size(max = 30)
        String username,

        @NotBlank(message = "Lösenord får inte vara tom.")
        @Size(max = 100)
        String password,

        @NotBlank(message = "role får inte vara tom.")
        @Size(max = 30)
        String role,

        @NotBlank(message = "Display name får inte vara tom")
        @Size(max = 20)
        String displayName,

        String profileImagePath,

        @NotBlank(message = "Email får inte vara tom.")
        @Size(max = 60)
        String email,

        @NotBlank(message = "Bio får inte vara tom.")
        @Size(max = 300)
        String bio) {
}
