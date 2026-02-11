package se.jensen.erik.socialmediaproject.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.erik.socialmediaproject.dto.PostRequestDto;
import se.jensen.erik.socialmediaproject.dto.PostResponseDto;
import se.jensen.erik.socialmediaproject.security.MyUserDetails;
import se.jensen.erik.socialmediaproject.service.PostService;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

/**
 * Controller för hantering av inlägg (posts).
 */
@RestController
@RequestMapping
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Skapar ett nytt inlägg för en specifik användare.
     * @param userId Användarens ID.
     * @param request Data för det nya inlägget.
     * @return Det skapade inlägget.
     */
    @PostMapping("/posts/user/{userId}")
    public ResponseEntity<PostResponseDto> createPostForUser(
            @PathVariable Long userId,
            @Valid @RequestBody PostRequestDto request
    ) {
        PostResponseDto response = postService.createPost(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Hämtar alla inlägg för en specifik användare.
     * @param userId Användarens ID.
     * @return En lista med inlägg.
     */
    @GetMapping("/posts/{userId}")
    public ResponseEntity<List<PostResponseDto>> getPostByUserId(@PathVariable Long userId) {
        List<PostResponseDto> response = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(response);
    }


    /**
     * Hämtar alla inlägg.
     * @return En lista med alla inlägg.
     */
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> response = postService.getAllPosts().stream()
                .map(post -> new PostResponseDto(post.getId(), post.getText(), post.getCreatedAt()))
                .toList();
        return ResponseEntity.ok(response);
    }


    /**
     * Uppdaterar ett befintligt inlägg.
     * @param postId ID för det inlägg som ska uppdateras.
     * @param dto Ny data för inlägget.
     * @param authentication Autentiseringsinformation för den inloggade användaren.
     * @return En ResponseEntity med det uppdaterade inlägget.
     */
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDto dto,
            Authentication authentication) {

        Long currentUserId = extractUserId(authentication);
        PostResponseDto response = postService.updatePost(postId, dto, currentUserId);
        return ResponseEntity.ok(response);
    }


    /**
     * Raderar ett inlägg.
     * @param postId ID för det inlägg som ska raderas.
     * @param authentication Autentiseringsinformation för den inloggade användaren.
     * @return En ResponseEntity med bekräftelse på radering.
     */
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, Authentication authentication){
        Long currentUserId = extractUserId(authentication);
        postService.deletePost(postId, currentUserId);
        return ResponseEntity.ok("Post removed with id: " + postId);
    }


    /**
     *
     * @param authentication utentiseringsinformation för den inloggade användaren.
     * @return details.getId, returnerar userID värdet efter long konvertering
     */

    private Long extractUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof MyUserDetails details) {
            return details.getId();
        }
        if (principal instanceof Jwt jwt) {
            Object claim = jwt.getClaim("userId");
            if (claim instanceof Number number) {
                return number.longValue();
            }
        }
        throw new IllegalStateException("Could not resolve current user id from authentication principal");
    }
}
