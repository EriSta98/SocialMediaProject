package se.jensen.erik.socialmediaproject.mapper;


import org.springframework.stereotype.Component;
import se.jensen.erik.socialmediaproject.dto.PostResponseDto;
import se.jensen.erik.socialmediaproject.dto.UserResponseDto;
import se.jensen.erik.socialmediaproject.dto.UserWithPostsResponseDto;
import se.jensen.erik.socialmediaproject.model.User;
import se.jensen.erik.socialmediaproject.dto.UserRequestDto;

import java.util.List;

@Component
public class UserMapper {

    public static User fromDto(UserRequestDto dto) {
        User user = new User();
        setUserValues(user, dto);
        return user;
    }

    public User fromDto(User user, UserRequestDto dto) {
        setUserValues(user, dto);
        return user;
    }

    private static void setUserValues(User user, UserRequestDto dto) {
        user.setProfileImagePath(dto.profileImagePath());
        user.setRole(dto.role());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setBio(dto.bio());
        user.setUsername(dto.username());
        user.setDisplayName(dto.displayName());
    }

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
