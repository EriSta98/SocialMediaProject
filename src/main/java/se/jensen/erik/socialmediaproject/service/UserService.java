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


/**
 * Tjänsteklass för hantering av användare.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * Konstruktor för UserService.
     * @param userRepository Repository för användare.
     * @param passwordEncoder Encoder för lösenord.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Uppdaterar en befintlig användare.
     * @param id ID för användaren som ska uppdateras.
     * @param dto Ny data för användaren.
     * @return En UserResponseDto med den uppdaterade användaren.
     * @throws RuntimeException Om användaren inte hittas.
     */
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found for update with id: {}", id);
                    return new RuntimeException("User not found");
                });


        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());
        user.setDisplayName(dto.displayName());
        user.setProfileImagePath(dto.profileImagePath());
        user.setBio(dto.bio());

        User saved = userRepository.save(user);
        return UserResponseDto.fromEntity(saved);
    }


    /**
     * Raderar en användare baserat på ID.
     * @param id Användarens ID.
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    /**
     * Hämtar alla användare i systemet.
     * @return En lista med UserResponseDto för alla användare.
     */
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserMapper.toDto(user)).toList();
    }


    /**
     * Hämtar en användare baserat på ID.
     * @param id Användarens ID.
     * @return UserResponseDto för den hittade användaren.
     * @throws NoSuchElementException Om användaren inte hittas.
     */
    public UserResponseDto getById(Long id){
        Optional<User> opt = userRepository.findById(id);

        if(!opt.isPresent()){
            logger.warn("User not found with id: {}", id);
            throw new NoSuchElementException("User not found with: " + id);
        }
        return UserMapper.toDto(opt.get());
    }


    /**
     * Lägger till en ny användare i systemet.
     * @param userDto Data för den nya användaren.
     * @return UserResponseDto för den skapade användaren.
     * @throws IllegalArgumentException Om användarnamn eller e-post redan finns.
     */
    public UserResponseDto addUser(UserRequestDto userDto) {
        User user = UserMapper.fromDto(userDto);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        logger.info("[DEBUG_LOG] Encoding password for new user {}. Plain: {}, Encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
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


    /**
     * Hjälpmetod för att konvertera en användare till DTO.
     * @param user Användaren.
     * @return En UserResponseDto.
     */
    private UserResponseDto toDto(User user){
        return UserMapper.toDto(user);
    }

    /**
     * Hjälpmetod för att skapa en användare från DTO.
     * @param userDto DTO:n.
     * @return En User-entitet.
     */
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

    /**
     * Hämtar en användare tillsammans med dess inlägg.
     * @param id Användarens ID.
     * @return En UserWithPostsResponseDto.
     * @throws NoSuchElementException Om användaren inte hittas.
     */
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
