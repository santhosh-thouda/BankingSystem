package banking;

import exceptions.*;
import java.math.BigDecimal;

/**
 * Factory pattern implementation for creating account objects
 */
public class AccountFactory {
    public static BankAccount createAccount(String accountType, 
                                          String accountNumber,
                                          String accountHolderName,
                                          String pin,
                                          BigDecimal balance) 
            throws IllegalArgumentException {
        if (accountType == null || accountNumber == null || 
            accountHolderName == null || pin == null || balance == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        switch (accountType.toUpperCase()) {
            case "SAVINGS":
                return new SavingsAccount(accountNumber, accountHolderName, pin, balance);
            case "CURRENT":
                return new CurrentAccount(accountNumber, accountHolderName, pin, balance);
            default:
                throw new IllegalArgumentException(
                    "Invalid account type. Must be SAVINGS or CURRENT");
        }
    }
}