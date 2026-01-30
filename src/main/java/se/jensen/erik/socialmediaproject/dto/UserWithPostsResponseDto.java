package se.jensen.erik.socialmediaproject.dto;

import java.util.List;

/**
 * Data Transfer Object som representerar en användare tillsammans med dess inlägg.
 * @param user Användarinformation.
 * @param posts Lista med användarens inlägg.
 */
public record UserWithPostsResponseDto(
        UserResponseDto user,
        List<PostResponseDto> posts
) {
}
