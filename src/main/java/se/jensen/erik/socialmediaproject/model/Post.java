package se.jensen.erik.socialmediaproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
/**
 * Entitetsklass som representerar ett inlägg (post) i databasen.
 */
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    /**
     * Standardkonstruktor.
     */
    public Post(){}


    /**
     * Hämtar inläggets text.
     * @return Textinnehåll.
     */
    public String getText() {
        return text;
    }

    /**
     * Sätter inläggets text.
     * @param text Textinnehåll.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Hämtar tidpunkt för skapande.
     * @return LocalDateTime då inlägget skapades.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sätter tidpunkt för skapande.
     * @param createdAt Tidpunkt för skapande.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Hämtar inläggets ID.
     * @return ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sätter inläggets ID.
     * @param id ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Hämtar användaren som skapat inlägget.
     * @return Användarentitet.
     */
    public User getUser() {
        return user;
    }

    /**
     * Kopplar en användare till inlägget.
     * @param user Användarentitet.
     */
    public void setUser(User user) {
        this.user = user;
    }


}
