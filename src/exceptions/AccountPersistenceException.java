package exceptions;

/**
 * Exception thrown when there is an error while persisting or retrieving account data
 */
public class AccountPersistenceException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountPersistenceException(String message) {
        super(message);
    }
    
    public AccountPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}