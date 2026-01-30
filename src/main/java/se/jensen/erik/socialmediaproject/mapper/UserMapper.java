package se.jensen.erik.socialmediaproject.mapper;


import org.springframework.stereotype.Component;
import se.jensen.erik.socialmediaproject.dto.PostResponseDto;
import se.jensen.erik.socialmediaproject.dto.UserResponseDto;
import se.jensen.erik.socialmediaproject.dto.UserWithPostsResponseDto;
import se.jensen.erik.socialmediaproject.model.User;
import se.jensen.erik.socialmediaproject.dto.UserRequestDto;

import java.util.List;

/**
 * Mapper-klass för att konvertera mellan användar-DTO:er och entiteter.
 */
@Component
public class UserMapper {

    /**
     * Skapar en User-entitet från en UserRequestDto.
     * @param dto Data för den nya användaren.
     * @return En ny User-entitet.
     */
    public static User fromDto(UserRequestDto dto) {
        User user = new User();
        setUserValues(user, dto);
        return user;
    }

    /**
     * Uppdaterar en befintlig User-entitet med värden från en UserRequestDto.
     * @param user Den befintliga entiteten.
     * @param dto Ny data.
     * @return Den uppdaterade entiteten.
     */
    public User fromDto(User user, UserRequestDto dto) {
        setUserValues(user, dto);
        return user;
    }

    /**
     * Sätter värden på en User-entitet från en UserRequestDto.
     * @param user Entiteten som ska uppdateras.
     * @param dto DTO:n med värden.
     */
    private static void setUserValues(User user, UserRequestDto dto) {
        user.setProfileImagePath(dto.profileImagePath());
        user.setRole(dto.role());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setBio(dto.bio());
        user.setUsername(dto.username());
        user.setDisplayName(dto.displayName());
    }

    /**
     * Konverterar en User-entitet till UserWithPostsResponseDto.
     * @param user Användaren inklusiv inlägg.
     * @return En UserWithPostsResponseDto.
     */
    public static UserWithPostsResponseDto toWithPostsDto(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDto userDto = toDto(user);

        List<PostResponseDto> postDtos = user.getPosts() == null
                ? List.of()
                : user.getPosts()
                .stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getText(),
                        post.getCreatedAt()
                ))
                .toList();

        return new UserWithPostsResponseDto(userDto, postDtos);
    }

    /**
     * Konverterar en User-entitet till UserResponseDto.
     * @param user Användarentiteten.
     * @return En UserResponseDto.
     */
    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getDisplayName(),
                user.getProfileImagePath(),
                user.getEmail(),
                user.getBio()
        );
    }
}
