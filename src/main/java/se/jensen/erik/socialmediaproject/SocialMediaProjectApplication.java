package se.jensen.erik.socialmediaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Huvudklassen för Social Media Project-applikationen.
 * Startar Spring Boot-applikationen.
 */
@SpringBootApplication
public class SocialMediaProjectApplication {

    /**
     * Startpunkt för applikationen.
     * @param args Kommandoradsargument.
     */
    public static void main(String[] args) {
        SpringApplication.run(SocialMediaProjectApplication.class, args);
    }

}
