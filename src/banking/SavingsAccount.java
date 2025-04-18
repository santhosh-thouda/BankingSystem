package banking;

import exceptions.*;
import util.CurrencyFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Savings account with interest and minimum balance requirements
 */
public class SavingsAccount extends BankAccount {
    private static final BigDecimal MINIMUM_BALANCE = new BigDecimal("1000.00");
    private static final BigDecimal MAX_TRANSACTION = new BigDecimal("100000.00");
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.04"); // 4% annual

    public SavingsAccount(String accountNumber, String accountHolderName,
                        String pin, BigDecimal balance) {
        super(accountNumber, accountHolderName, pin, balance);
    }

    @Override
    public void withdraw(BigDecimal amount) 
            throws InsufficientFundsException, InvalidAmountException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (amount.compareTo(MAX_TRANSACTION) > 0) {
            throw new MaximumAmountException(
                "Exceeds maximum transaction limit of " + 
                CurrencyFormatter.format(MAX_TRANSACTION)
            );
        }
        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance.compareTo(MINIMUM_BALANCE) < 0) {
            throw new InsufficientFundsException(
                String.format("Minimum balance %s required. Available: %s",
                    CurrencyFormatter.format(MINIMUM_BALANCE),
                    CurrencyFormatter.format(newBalance))
            );
        }
        balance = newBalance;
    }
    
 // Temporary workaround
    throw new InsufficientFundsException(
        String.format("Minimum balance $%,.2f required", MINIMUM_BALANCE)
    );

    public BigDecimal applyInterest() {
        BigDecimal monthlyInterest = balance.multiply(INTEREST_RATE)
                                         .divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
        balance = balance.add(monthlyInterest);
        return monthlyInterest;
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }
}