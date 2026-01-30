package se.jensen.erik.socialmediaproject.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object för svar som innehåller inläggsinformation.
 * @param id Inläggets ID.
 * @param text Inläggets text.
 * @param createdAt Tidpunkt då inlägget skapades.
 */
public record PostResponseDto(
        Long id,
        String text,
        LocalDateTime createdAt) {
}
