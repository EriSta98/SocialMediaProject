package se.jensen.erik.socialmediaproject.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.erik.socialmediaproject.dto.*;
import se.jensen.erik.socialmediaproject.service.PostService;
import se.jensen.erik.socialmediaproject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService service;

    private final PostService postService;


    public UserController(UserService service, PostService postService) {
        this.service = service;
        this.postService = postService;
    }




    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> allUsers = service.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto userResponseDTO = service.getById(id);
        return ResponseEntity.ok().body(userResponseDTO);
    }


    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addUser(dto));
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/{userId}/posts")
    public ResponseEntity<PostResponseDto> createPostForUser(
            @PathVariable Long userId,
            @Valid @RequestBody PostRequestDto request
    ) {

        PostResponseDto response = postService.createPost(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}/with-posts")
    public ResponseEntity<UserWithPostsResponseDto> getUserWithPosts(@PathVariable Long id) {

        UserWithPostsResponseDto response = service.getUserWithPosts(id);

        return ResponseEntity.ok(response);
    }


}
