package exceptions;

import java.math.BigDecimal;

/**
 * Exception thrown when a transaction exceeds maximum allowed amount
 */
public class MaximumAmountException extends AccountTransactionException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BigDecimal attemptedAmount;
    private final BigDecimal maxAllowed;

    public MaximumAmountException(String message) {
        super(message);
        this.attemptedAmount = attemptedAmount;
        this.maxAllowed = maxAllowed;
    }
    
    public BigDecimal getAttemptedAmount() {
        return attemptedAmount;
    }
    
    public BigDecimal getMaxAllowed() {
        return maxAllowed;
    }
}