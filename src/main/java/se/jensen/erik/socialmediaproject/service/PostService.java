package se.jensen.erik.socialmediaproject.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.jensen.erik.socialmediaproject.dto.PostRequestDto;
import se.jensen.erik.socialmediaproject.dto.PostResponseDto;
import se.jensen.erik.socialmediaproject.model.Post;
import se.jensen.erik.socialmediaproject.model.User;
import se.jensen.erik.socialmediaproject.repository.PostRepository;
import se.jensen.erik.socialmediaproject.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tjänsteklass för hantering av inlägg (posts).
 */
@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    /**
     * Konstruktor för PostService.
     * @param userRepository Repository för användare.
     * @param postRepository Repository för inlägg.
     */
    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    /**
     * Skapar ett nytt inlägg för en specifik användare.
     * @param userId ID för användaren som skapar inlägget.
     * @param postDTO Data för det nya inlägget.
     * @return En PostResponseDto med det sparade inlägget.
     * @throws NoSuchElementException Om användaren inte hittas.
     */
    public PostResponseDto createPost(Long userId, PostRequestDto postDTO){
        Post post = new Post();
        post.setText(postDTO.text());
        post.setCreatedAt(LocalDateTime.now());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    String message = "User not found with id " + userId;
                    logger.warn(message);
                    return new NoSuchElementException(message);
                });


        post.setUser(user);
        Post fromDb = postRepository.save(post);
        return new PostResponseDto(fromDb.getId(), fromDb.getText(), fromDb.getCreatedAt());

    }

    /**
     * Hämtar alla inlägg.
     * @return En lista med alla Post-entiteter.
     */
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }



}
