package se.jensen.erik.socialmediaproject.dto;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String text,
        LocalDateTime createdAt) {
}
