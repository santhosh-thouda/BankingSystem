package exceptions;

/**
 * Exception thrown when authentication fails (invalid PIN, etc.)
 */
public class AuthenticationException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String accountNumber;
    
    public AuthenticationException(String accountNumber, String message) {
        super(message);
        this.accountNumber = accountNumber;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
}