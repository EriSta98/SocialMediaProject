package se.jensen.erik.socialmediaproject.dto;

import se.jensen.erik.socialmediaproject.model.User;

public record UserResponseDto(
        Long id,
        String username,
        String role,
        String displayName,
        String profileImagePath,
        String email,
        String bio) {


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



