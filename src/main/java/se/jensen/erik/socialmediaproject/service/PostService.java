package se.jensen.erik.socialmediaproject.service;


import org.slf4j.Logger; // AI-Skapad kod.
import org.slf4j.LoggerFactory; // AI-Skapad kod.
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

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class); // AI-Skapad kod.
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public PostResponseDto createPost(Long userId, PostRequestDto postDTO){
        Post post = new Post();
        post.setText(postDTO.text());
        post.setCreatedAt(LocalDateTime.now());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    String message = "User not found with id " + userId; // AI-Skapad kod.
                    logger.warn(message);
                    return new NoSuchElementException(message);
                });


        post.setUser(user);
        Post fromDb = postRepository.save(post);
        return new PostResponseDto(fromDb.getId(), fromDb.getText(), fromDb.getCreatedAt());

    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }



}
