package exceptions;

/**
 * Exception thrown when an account has insufficient funds for a transaction
 */
public class InsufficientFundsException extends AccountTransactionException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String message) {
        super(message);
    }
    
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}