package se.jensen.erik.socialmediaproject.dto;

/**
 * Builder-klass för att skapa UserResponseDto-objekt.
 */
public final class UserResponseDtoBuilder {
    private Long id;
    private String username;
    private String role;
    private String displayName;
    private String profileImagePath;
    private String email;
    private String bio;

    private UserResponseDtoBuilder() {}

    /**
     * Skapar en ny instans av builder.
     * @return En ny UserResponseDtoBuilder.
     */
    public static UserResponseDtoBuilder builder() {
        return new UserResponseDtoBuilder();
    }

    /**
     * Skapar en builder baserad på en befintlig UserResponseDto.
     * @param dto Existerande DTO.
     * @return En builder förpopulerad med värden från DTO:n.
     */
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

    /**
     * Sätter ID.
     * @param id Användar-ID.
     * @return Buildern själv.
     */
    public UserResponseDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sätter användarnamn.
     * @param username Användarnamn.
     * @return Buildern själv.
     */
    public UserResponseDtoBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sätter roll.
     * @param role Roll.
     * @return Buildern själv.
     */
    public UserResponseDtoBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    /**
     * Sätter visningsnamn.
     * @param displayName Visningsnamn.
     * @return Buildern själv.
     */
    public UserResponseDtoBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Sätter sökväg till profilbild.
     * @param profileImagePath Sökväg till bild.
     * @return Buildern själv.
     */
    public UserResponseDtoBuilder withProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
        return this;
    }

    /**
     * Sätter e-post.
     * @param email E-postadress.
     * @return Buildern själv.
     */
    public UserResponseDtoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sätter biografi.
     * @param bio Biografi.
     * @return Buildern själv.
     */
    public UserResponseDtoBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    /**
     * Bygger det färdiga UserResponseDto-objektet.
     * @return Ett nytt UserResponseDto-objekt.
     */
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
