package exceptions;

import java.math.BigDecimal;

/**
 * Exception thrown when a withdrawal would violate minimum balance requirements
 */
public class MinimumAccountBalanceException extends AccountTransactionException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BigDecimal currentBalance;
    private final BigDecimal minimumBalance;
    private final BigDecimal attemptedWithdrawal;

    public MinimumAccountBalanceException(BigDecimal currentBalance, 
                                        BigDecimal minimumBalance, 
                                        BigDecimal attemptedWithdrawal) {
        super(String.format(
            "Withdrawal of %.2f would reduce balance below minimum required balance of %.2f (Current: %.2f)",
            attemptedWithdrawal, minimumBalance, currentBalance));
        this.currentBalance = currentBalance;
        this.minimumBalance = minimumBalance;
        this.attemptedWithdrawal = attemptedWithdrawal;
    }
    
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
    
    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }
    
    public BigDecimal getAttemptedWithdrawal() {
        return attemptedWithdrawal;
    }
}