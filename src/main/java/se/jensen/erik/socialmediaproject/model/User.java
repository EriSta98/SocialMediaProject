package se.jensen.erik.socialmediaproject.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entitetsklass som representerar en användare i databasen.
 */
@Entity
@Table(name = "app_user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "profile_image_path")
    private String profileImagePath;

    @Column(nullable = false)
    private String bio;

    @Column(unique = true, nullable = false)
    private String email;


    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> posts;


    /**
     * Standardkonstruktor.
     */
    public User(){}

    /**
     * Hämtar lösenordet.
     * @return Lösenordet.
     */
    public String getPassword() {return password;}

    /**
     * Hämtar biografi.
     * @return Biografi.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sätter biografi.
     * @param bio Biografi.
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Hämtar ID.
     * @return ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sätter ID.
     * @param id ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Hämtar användarnamn.
     * @return Användarnamn.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sätter användarnamn.
     * @param username Användarnamn.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sätter lösenordet.
     * @param password Lösenordet.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Hämtar roll.
     * @return Roll (t.ex. USER, ADMIN).
     */
    public String getRole() {return role;}

    /**
     * Sätter roll.
     * @param role Roll.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Hämtar visningsnamn.
     * @return Visningsnamn.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sätter visningsnamn.
     * @param displayName Visningsnamn.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Hämtar sökväg till profilbild.
     * @return Sökväg.
     */
    public String getProfileImagePath() {
        return profileImagePath;
    }

    /**
     * Sätter sökväg till profilbild.
     * @param profileImagePath Sökväg.
     */
    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    /**
     * Hämtar e-post.
     * @return E-postadress.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sätter e-post.
     * @param email E-postadress.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Hämtar användarens alla inlägg.
     * @return En lista med inlägg.
     */
    public List<Post> getPosts() {
        return posts;
    }



}
