package se.jensen.erik.socialmediaproject.dto;

public final class UserResponseDtoBuilder {
    private Long id;
    private String username;
    private String role;
    private String displayName;
    private String profileImagePath;
    private String email;
    private String bio;

    private UserResponseDtoBuilder() {}

    public static UserResponseDtoBuilder builder() {
        return new UserResponseDtoBuilder();
    }

    public static UserResponseDtoBuilder from(UserResponseDto dto) {
        UserResponseDtoBuilder b = new UserResponseDtoBuilder();
        if (dto == null) return b;
        b.id = dto.id();
        b.username = dto.username();
        b.role = dto.role();
        b.displayName = dto.displayName();
        b.profileImagePath = dto.profileImagePath();
        b.email = dto.email();
        b.bio = dto.bio();
        return b;
    }

    public UserResponseDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserResponseDtoBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserResponseDtoBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public UserResponseDtoBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public UserResponseDtoBuilder withProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
        return this;
    }

    public UserResponseDtoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserResponseDtoBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserResponseDto build() {
        return new UserResponseDto(
                id,
                username,
                role,
                displayName,
                profileImagePath,
                email,
                bio
        );
    }
}
