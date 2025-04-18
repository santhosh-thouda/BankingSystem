package exceptions;

/**
 * Exception thrown when database initialization fails
 */
public class DatabaseInitializationException extends Exception {
    public DatabaseInitializationException(String message) {
        super(message);
    }
    
    public DatabaseInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}