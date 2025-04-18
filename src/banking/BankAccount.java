package banking;

import exceptions.*;
import java.math.BigDecimal;

/**
 * Abstract base class representing a bank account
 */
public abstract class BankAccount {
    protected final String accountNumber;
    protected final String accountHolderName;
    protected final String pin;
    protected BigDecimal balance;
    
    

    public BankAccount(String accountNumber, String accountHolderName, 
                     String pin, BigDecimal balance) {
        if (accountNumber == null || accountHolderName == null || 
            pin == null || balance == null) {
            throw new IllegalArgumentException("Account parameters cannot be null");
        }
        
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = balance;
    }
    
    public String getPin() {
        return pin;
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }

    public void deposit(BigDecimal amount) throws InvalidAmountException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        balance = balance.add(amount);
    }

    public abstract void withdraw(BigDecimal amount) 
        throws InsufficientFundsException, InvalidAmountException;

    public void transfer(BankAccount recipient, BigDecimal amount) 
            throws MaximumAmountException, InsufficientFundsException, InvalidAmountException {
        this.withdraw(amount); // These exceptions will propagate up
        recipient.deposit(amount);
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public BigDecimal getBalance() { return balance; }
    public abstract String getAccountType();

    @Override
    public String toString() {
        return String.format("%s Account[number=%s, holder=%s, balance=%.2f]",
                getAccountType(), accountNumber, accountHolderName, balance);
    }
}