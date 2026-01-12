package se.jensen.erik.socialmediaproject.dto;


import jakarta.validation.constraints.*;

public record PostRequestDto(
        @NotBlank(message = "Text får inte vara tom.")
        @Size(min = 3, max = 200)
        String text,

        @NotNull(message = "ID får inte vara null")
        @PositiveOrZero
        Long id) {
}
