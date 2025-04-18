package exceptions;

/**
 * Exception thrown when there is an error during financial transactions
 */
public class AccountTransactionException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountTransactionException(String message) {
        super(message);
    }
    
    public AccountTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}