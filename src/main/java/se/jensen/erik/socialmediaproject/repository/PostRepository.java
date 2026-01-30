package se.jensen.erik.socialmediaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.erik.socialmediaproject.model.Post;

/**
 * Repository-interface för att hantera databasoperationer för Post-entiteter.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
