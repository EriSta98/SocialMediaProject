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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found for update with id: {}", id);
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
        List<User> users = userRepository.findAll();

        if (users.isEmpty()){
            logger.info("No users found in database");
            throw new RuntimeException("No Users found in database");
        } else if(users.size() == 1){
            throw new IllegalArgumentException("Only one user found in database.");
        }
        return users.stream().map(user -> userMapper.toDto(user)).toList();
    }


    public UserResponseDto getById(Long id){
        Optional<User> opt = userRepository.findById(id);

        if(!opt.isPresent()){
            logger.warn("User not found with id: {}", id);
            throw new NoSuchElementException("User not found with: " + id);
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
            logger.info("Attempt to add user with existing username or email: {} / {}", user.getUsername(), user.getEmail());
            throw new IllegalArgumentException(
                    "User with this username or email already exists"
            );
        }



        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }


    private UserResponseDto toDto(User user){
        return UserMapper.toDto(user);
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
                    logger.warn("User with posts not found with id: {}", id);
                    return new NoSuchElementException("User not found with: " + id);
                });

        UserWithPostsResponseDto dto = UserMapper.toWithPostsDto(user);
        return dto;
    }



}
