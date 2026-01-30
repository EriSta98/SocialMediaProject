package se.jensen.erik.socialmediaproject.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.erik.socialmediaproject.dto.*;
import se.jensen.erik.socialmediaproject.service.PostService;
import se.jensen.erik.socialmediaproject.service.UserService;

import java.util.List;

/**
 * Controller för hantering av användare.
 */
@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService service;

    private final PostService postService;


    /**
     * Konstruktor för UserController.
     * @param service Tjänst för användarhantering.
     * @param postService Tjänst för inläggshantering.
     */
    public UserController(UserService service, PostService postService) {
        this.service = service;
        this.postService = postService;
    }




    /**
     * Hämtar alla användare.
     * @return En lista med alla användare.
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> allUsers = service.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }


    /**
     * Hämtar en användare baserat på ID.
     * @param id Användarens ID.
     * @return Den hittade användaren.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto userResponseDTO = service.getById(id);
        return ResponseEntity.ok().body(userResponseDTO);
    }


    /**
     * Skapar en ny användare.
     * @param dto Data för den nya användaren.
     * @return Den skapade användaren.
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addUser(dto));
    }



    /**
     * Uppdaterar en befintlig användare.
     * @param id ID för den användare som ska uppdateras.
     * @param dto Ny data för användaren.
     * @return Den uppdaterade användaren.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }


    /**
     * Raderar en användare.
     * @param id ID för den användare som ska raderas.
     * @return En tom ResponseEntity med status 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * Skapar ett nytt inlägg för en specifik användare.
     * @param userId Användarens ID.
     * @param request Data för det nya inlägget.
     * @return Det skapade inlägget.
     */
    @PostMapping("/{userId}/posts")
    public ResponseEntity<PostResponseDto> createPostForUser(
            @PathVariable Long userId,
            @Valid @RequestBody PostRequestDto request
    ) {

        PostResponseDto response = postService.createPost(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Hämtar en användare inklusiv dess inlägg.
     * @param id Användarens ID.
     * @return Användaren med dess inlägg.
     */
    @GetMapping("/{id}/with-posts")
    public ResponseEntity<UserWithPostsResponseDto> getUserWithPosts(@PathVariable Long id) {

        UserWithPostsResponseDto response = service.getUserWithPosts(id);

        return ResponseEntity.ok(response);
    }


}
