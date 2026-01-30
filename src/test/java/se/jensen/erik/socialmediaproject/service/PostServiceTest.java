package se.jensen.erik.socialmediaproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.erik.socialmediaproject.dto.PostRequestDto;
import se.jensen.erik.socialmediaproject.dto.PostResponseDto;
import se.jensen.erik.socialmediaproject.model.Post;
import se.jensen.erik.socialmediaproject.model.User;
import se.jensen.erik.socialmediaproject.repository.PostRepository;
import se.jensen.erik.socialmediaproject.repository.UserRepository;

import java.util.Optional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;


    /**
     * * Test för att verifiera att createPost-metoden skapar ett inlägg korrekt när användaren finns.
     */
    @Test
    public void testCreatePost_Success() {
        // Arrange
        Long userId = 1L;
        PostRequestDto requestDto = new PostRequestDto("Hello World", 0L);
        User user = new User();
        user.setId(userId);
        
        Post savedPost = new Post();
        savedPost.setId(10L);
        savedPost.setText("Hello World");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // Act
        PostResponseDto result = postService.createPost(userId, requestDto);

        // Assert
        assertNotNull(result);
        assertEquals("Hello World", result.text());
        assertEquals(10L, result.id());
        verify(userRepository, times(1)).findById(userId);
        verify(postRepository, times(1)).save(any(Post.class));
    }


    /**
     * Test för att verifiera att createPost-metoden kastar ett undantag när användaren inte finns.
     */
    @Test
    public void testCreatePost_UserNotFound() {
        // Arrange
        Long userId = 99L;
        PostRequestDto requestDto = new PostRequestDto("Hello World", 0L);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> postService.createPost(userId, requestDto));
        verify(userRepository, times(1)).findById(userId);
        verify(postRepository, never()).save(any(Post.class));
    }
}
