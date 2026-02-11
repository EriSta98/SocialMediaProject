package se.jensen.erik.socialmediaproject.controller;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.erik.socialmediaproject.dto.*;
import se.jensen.erik.socialmediaproject.service.UserService;

import java.util.List;

/**
 * Controller för hantering av användare.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;



    /**
     * Konstruktor för UserController.
     * @param service Tjänst för användarhantering.
     */
    public UserController(UserService service) {
        this.service = service;
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
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto dto) {
        logger.info("[DEBUG_LOG] Creating user: {}", dto.username());
        UserResponseDto response = service.addUser(dto);
        logger.info("[DEBUG_LOG] User created successfully: {}", response.username());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    /**
     * Uppdaterar en befintlig användare.
     * Man kan för tillfället uppdatera andra användare utan att logga in som dom.
     * Kommer lämna det så för debug anledningar.
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
