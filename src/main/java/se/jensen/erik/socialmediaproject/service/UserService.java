package se.jensen.erik.socialmediaproject.service;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.erik.socialmediaproject.dto.UserRequestDto;
import se.jensen.erik.socialmediaproject.dto.UserResponseDto;
import se.jensen.erik.socialmediaproject.dto.UserWithPostsResponseDto;
import se.jensen.erik.socialmediaproject.mapper.UserMapper;
import se.jensen.erik.socialmediaproject.model.User;
import se.jensen.erik.socialmediaproject.repository.UserRepository;

import java.util.List;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class); // AI-skapad kod
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with id: {}", id); // AI-skapad kod
                    return new RuntimeException("User not found");
                });

        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setDisplayName(dto.displayName());
        user.setProfileImagePath(dto.profileImagePath());
        user.setBio(dto.bio());

        User saved = userRepository.save(user);
        return UserResponseDto.fromEntity(saved);
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }


    public UserResponseDto getById(Long id){
        Optional<User> opt = userRepository.findById(id);

        if(opt.isPresent()){
            logger.warn("User not found with id: {}", id); // AI-skapad kod
            throw new NoSuchElementException("User not found with: ");
        }
        return UserMapper.toDto(opt.get());
    }


    public UserResponseDto addUser(UserRequestDto userDto) {
        User user = UserMapper.fromDto(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean exists = userRepository.existsByUsernameOrEmail(
                user.getUsername(),
                user.getEmail()
        );

        if(exists){
            throw new IllegalArgumentException(
                    "User med detta username eller email finns redan i databasen"
            );
        }



        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }


    private UserResponseDto toDto(User user){
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

    private User fromDto(UserRequestDto userDto){
        User user = new User();
        user.setBio(userDto.bio());
        user.setDisplayName(userDto.displayName());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        user.setRole(userDto.role());
        user.setProfileImagePath(userDto.profileImagePath());
        user.setUsername(userDto.username());
        return user;
    }

    public UserWithPostsResponseDto getUserWithPosts(Long id) {
        User user = userRepository.findUserWithPosts(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with id: {}", id); // AI-skapad kod
                    return new NoSuchElementException("User not found with: " + id);
                }); // AI-skapade kod

        UserWithPostsResponseDto dto = UserMapper.toWithPostsDto(user);
        return dto;
    }



}
