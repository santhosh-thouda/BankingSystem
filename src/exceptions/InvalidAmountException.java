package exceptions;

/**
 * Exception thrown when an invalid amount is provided for a transaction
 */
public class InvalidAmountException extends AccountTransactionException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5816970100459961398L;

	public InvalidAmountException(String message) {
        super(message);
    }
    
    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}