package banking;

import exceptions.*;
import java.math.BigDecimal;

public class CurrentAccount extends BankAccount {
    private static final BigDecimal OVERDRAFT_LIMIT = new BigDecimal("-10000.00");
    private static final BigDecimal MAX_TRANSACTION = new BigDecimal("200000.00");

    public CurrentAccount(String accountNumber, String accountHolderName, String pin, BigDecimal balance) {
        super(accountNumber, accountHolderName, pin, balance);
    }

    @Override
    public void withdraw(BigDecimal amount) throws InsufficientFundsException, InvalidAmountException, MaximumAmountException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (amount.compareTo(MAX_TRANSACTION) > 0) {
            throw new MaximumAmountException("Exceeds maximum transaction limit");
        }
        if (balance.subtract(amount).compareTo(OVERDRAFT_LIMIT) < 0) {
            throw new InsufficientFundsException("Would exceed overdraft limit");
        }
        balance = balance.subtract(amount);
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }
}