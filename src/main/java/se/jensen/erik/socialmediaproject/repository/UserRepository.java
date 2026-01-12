package se.jensen.erik.socialmediaproject.repository;

import org.springframework.data.jpa.repository.Query;
import se.jensen.erik.socialmediaproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   boolean existsByUsernameOrEmail(String username, String email);

   Optional<User> findByUsername(String username);


   @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.id = :id")
   Optional<User> findUserWithPosts(Long id);




}


