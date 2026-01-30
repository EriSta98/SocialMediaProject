package se.jensen.erik.socialmediaproject.repository;

import org.springframework.data.jpa.repository.Query;
import se.jensen.erik.socialmediaproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository-interface för att hantera databasoperationer för User-entiteter.
 */
public interface UserRepository extends JpaRepository<User, Long> {

   /**
    * Kontrollerar om en användare med angivet användarnamn eller e-post redan finns.
    * @param username Användarnamn att kontrollera.
    * @param email E-post att kontrollera.
    * @return sant om användaren finns, annars falskt.
    */
   boolean existsByUsernameOrEmail(String username, String email);

   /**
    * Hittar en användare baserat på användarnamn.
    * @param username Användarnamn.
    * @return En Optional med den hittade användaren.
    */
   Optional<User> findByUsername(String username);


   /**
    * Hämtar en användare och dess inlägg med en JOIN FETCH-fråga.
    * @param id Användarens ID.
    * @return En Optional med användaren inklusiv inlägg.
    */
   @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.id = :id")
   Optional<User> findUserWithPosts(Long id);




}


