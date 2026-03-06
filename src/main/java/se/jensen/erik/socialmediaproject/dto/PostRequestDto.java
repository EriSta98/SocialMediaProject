package se.jensen.erik.socialmediaproject.dto;


import jakarta.validation.constraints.*;

/**
 * Data Transfer Object för att skapa eller uppdatera inlägg.
 * @param text Inläggets textinnehåll.
 * @param id Relaterat ID (t.ex. användar-ID eller inläggs-ID beroende på kontext).
 */
public record PostRequestDto(
        @NotBlank(message = "Text cannot be empty.")
        @Size(min = 3, max = 200)
        String text,

        @NotNull(message = "ID cannot be null")
        @PositiveOrZero
        Long id) {
}
