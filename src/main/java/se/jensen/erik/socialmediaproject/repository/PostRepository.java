package se.jensen.erik.socialmediaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.erik.socialmediaproject.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
