package se.jensen.erik.socialmediaproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.erik.socialmediaproject.dto.UserRequestDto;
import se.jensen.erik.socialmediaproject.dto.UserResponseDto;

import se.jensen.erik.socialmediaproject.model.User;
import se.jensen.erik.socialmediaproject.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    /**
     * Test för att verifiera att getById-metoden returnerar korrekt användardata när användaren finns.
     * Verifierar att en användare kan hämtas korrekt.
     */
    @Test
    public void testGetById_Success() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("Erik");
        user.setEmail("erik@example.com");
        user.setRole("USER");
        user.setDisplayName("Erik S");
        user.setBio("Bio");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserResponseDto result = userService.getById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals("Erik", result.username());
        assertEquals("erik@example.com", result.email());
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Test för att verifiera att getById-metoden kastar ett undantag
     * Och rätt undantag(Exception) när användaren inte finns.
     *
     */
    @Test
    public void testGetById_NotFound() {
        // Arrange
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.getById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Test för att verifiera att addUser-metoden sparar en ny användare korrekt när användarnamn och e-post inte redan finns.
     * Verifierar att en användare kan läggas till och att lösenordet krypteras korrekt.
     */
    @Test
    public void testAddUser_Success() {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto("Sven", "password123", "USER", "Sven S", "path", "sven@example.com", "Bio");
        
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(false);
        
        User savedUser = new User();
        savedUser.setId(2L);
        savedUser.setUsername("Sven");
        savedUser.setEmail("sven@example.com");
        savedUser.setRole("USER");
        savedUser.setDisplayName("Sven S");
        savedUser.setBio("Bio");
        savedUser.setProfileImagePath("path");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponseDto result = userService.addUser(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals("Sven", result.username());
        assertEquals("sven@example.com", result.email());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
