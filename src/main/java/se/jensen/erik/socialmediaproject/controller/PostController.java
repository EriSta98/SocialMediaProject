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

@RestController
@RequestMapping("/posts")
public class PostController {

    private List<Post> posts = new ArrayList<>();


    //Lägg till en post
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


    // Hämta en post med specifikt ID
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


    // Hämta alla posts
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


    //Uppdatera en post
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


    //Radera post
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
