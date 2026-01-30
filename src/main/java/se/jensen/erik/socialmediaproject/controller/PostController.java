package se.jensen.erik.socialmediaproject.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.erik.socialmediaproject.dto.PostRequestDto;
import se.jensen.erik.socialmediaproject.dto.PostResponseDto;
import se.jensen.erik.socialmediaproject.model.Post;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller för hantering av inlägg (posts).
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final List<Post> posts = new ArrayList<>();


    /**
     * Skapar ett nytt inlägg.
     * @param dto Data för det nya inlägget.
     * @return En ResponseEntity med det skapade inlägget.
     */
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto dto) {

        Post post = new Post();
        post.setText(dto.text());
        post.setId(dto.id());

        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);

        posts.add(post);

        PostResponseDto response =
                new PostResponseDto((long) post.getId(),
                        post.getText(),
                        post.getCreatedAt());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Hämtar alla inlägg för en specifik användare.
     * @param userId Användarens ID.
     * @return En lista med inlägg.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<PostResponseDto>> getPostById(@PathVariable int userId) {

        List<PostResponseDto> response = posts.stream()
                .filter(post -> post.getId() == userId)
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getText(),
                        post.getCreatedAt()))
                .toList();

        if(response.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }


    /**
     * Hämtar alla inlägg.
     * @return En lista med alla inlägg.
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> response =
                posts.stream()
                        .map(post -> new PostResponseDto(
                                post.getId(),
                                post.getText(),
                                post.getCreatedAt()))
                        .toList();
        return ResponseEntity.ok(response);
    }


    /**
     * Uppdaterar ett befintligt inlägg.
     * @param userId ID för det inlägg som ska uppdateras.
     * @param dto Ny data för inlägget.
     * @return En ResponseEntity med det uppdaterade inlägget.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable int userId,
            @Valid @RequestBody PostRequestDto dto) {



        Post p = posts.stream()
                .filter(post -> post.getId() == userId)
                .findFirst()
                .orElse(null);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }


        // uppdatera värden från DTO
        p.setText(dto.text());

        // skapa responseDTO
        PostResponseDto response =
                new PostResponseDto(
                        (long) p.getId(),
                        p.getText(),
                        p.getCreatedAt()
                );
        // returnera 200 OK + DTO
        return ResponseEntity.ok(response);
    }


    /**
     * Raderar ett inlägg.
     * @param userId ID för det inlägg som ska raderas.
     * @return En ResponseEntity med bekräftelse på radering.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deletePost(@PathVariable int userId){
        Post p = posts.stream()
                .filter(post -> post.getId() == userId)
                .findFirst()
                .orElse(null);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        posts.remove(p);
        return ResponseEntity.ok("Post removed: " + p.getText());
    }

}
