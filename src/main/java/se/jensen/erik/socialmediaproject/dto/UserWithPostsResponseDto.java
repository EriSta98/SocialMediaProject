package se.jensen.erik.socialmediaproject.dto;

import java.util.List;

public record UserWithPostsResponseDto(
        UserResponseDto user,
        List<PostResponseDto> posts
) {
}
