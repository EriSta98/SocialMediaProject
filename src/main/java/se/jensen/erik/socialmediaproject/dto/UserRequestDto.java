package se.jensen.erik.socialmediaproject.dto;


import jakarta.validation.constraints.Email;
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
        @NotBlank(message = "Name cannot be empty.")
        @Size(max = 30)
        String username,

        @NotBlank(message = "Password cannot be empty.")
        @Size(max = 100)
        String password,

        @NotBlank(message = "Role cannot be empty.")
        @Size(max = 30)
        String role,

        @NotBlank(message = "Display name cannot be empty.")
        @Size(max = 20)
        String displayName,

        String profileImagePath,

        @NotBlank(message = "Email cannot be empty.")
        @Size(max = 60)
        @Email(message = "Invalid email format.")
        String email,

        @NotBlank(message = "Bio cannot be empty.")
        @Size(max = 300)
        String bio) {
}
