package se.jensen.erik.socialmediaproject.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Global felhanterare för applikationen.
 * Fångar upp specifika undantag och returnerar lämpliga HTTP-svar.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Hanterar valideringsfel för inkommande requests.
     * @param ex Undantaget som kastas vid misslyckad validering.
     * @return En karta med fältnamn och felmeddelanden.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        logger.warn("Validation error: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        for(org.springframework.validation.FieldError fieldError
                : ex.getBindingResult().getFieldErrors()) {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();

            errors.put(fieldName, message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Hanterar fall där ett efterfrågat element inte hittas.
     * @param ex Undantaget som kastas.
     * @return Felmeddelande med status 404.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElement(NoSuchElementException ex) {
        logger.warn("Element not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Hanterar ogiltiga argument (t.ex. när användare redan finns).
     * @param ex Undantaget.
     * @return Felmeddelande med status 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Illegal argument: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Hanterar alla andra oväntade fel.
     * @param ex Undantaget.
     * @return Felmeddelande med status 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("Unhandled exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ett oväntat fel uppstod: " + ex.getMessage());
    }
}
