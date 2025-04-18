package service;

import banking.BankAccount;
import banking.CurrentAccount;
import banking.SavingsAccount;
import exceptions.*;
import repository.AccountRepository;
import repository.TransactionRepository;
import util.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, 
                         TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public BankAccount createAccount(String accountNumber, String accountHolderName,
                                   String pin, String accountType, BigDecimal initialDeposit) 
            throws AccountPersistenceException, InvalidAmountException {
        InputValidator.validateAccountNumber(accountNumber);
        InputValidator.validateName(accountHolderName);
        InputValidator.validateAmount(initialDeposit);

        if (accountRepository.accountExists(accountNumber)) {
            throw new IllegalArgumentException("Account number already exists");
        }

        BankAccount account = accountType.equalsIgnoreCase("SAVINGS") ?
                new SavingsAccount(accountNumber, accountHolderName, pin, initialDeposit) :
                new CurrentAccount(accountNumber, accountHolderName, pin, initialDeposit);

        accountRepository.save(account);
        transactionRepository.logTransaction(
                accountNumber, 
                "ACCOUNT_CREATION", 
                initialDeposit, 
                LocalDateTime.now()
        );
        return account;
    }

    public BankAccount getAccount(String accountNumber, String pin) 
            throws AccountPersistenceException, AuthenticationException {
        BankAccount account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new AccountPersistenceException("Account not found");
        }
        if (!account.validatePin(pin)) {
            throw new AuthenticationException(accountNumber, "Invalid PIN");
        }
        return account;
    }

    public void deposit(String accountNumber, BigDecimal amount) 
            throws AccountPersistenceException, InvalidAmountException {
        BankAccount account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new AccountPersistenceException("Account not found");
        }

        account.deposit(amount);
        accountRepository.updateBalance(account);
        transactionRepository.logTransaction(
                accountNumber, 
                "DEPOSIT", 
                amount, 
                LocalDateTime.now()
        );
    }

    public void withdraw(String accountNumber, String pin, BigDecimal amount) 
            throws AccountPersistenceException, AccountTransactionException {
        BankAccount account = getAccount(accountNumber, pin);
        account.withdraw(amount);
        accountRepository.updateBalance(account);
        transactionRepository.logTransaction(
                accountNumber, 
                "WITHDRAWAL", 
                amount, 
                LocalDateTime.now()
        );
    }

    public void transfer(String fromAccountNumber, String pin, 
                        String toAccountNumber, BigDecimal amount) 
            throws AccountTransactionException, AccountPersistenceException {
        BankAccount source = getAccount(fromAccountNumber, pin);
        BankAccount destination = accountRepository.findByAccountNumber(toAccountNumber);
        
        if (destination == null) {
            throw new AccountPersistenceException("Recipient account not found");
        }
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        source.transfer(destination, amount);
        accountRepository.updateBalance(source);
        accountRepository.updateBalance(destination);
        
        transactionRepository.logTransaction(
                fromAccountNumber, 
                "TRANSFER_OUT", 
                amount, 
                LocalDateTime.now()
        );
        transactionRepository.logTransaction(
                toAccountNumber, 
                "TRANSFER_IN", 
                amount, 
                LocalDateTime.now()
        );
    }

    public BigDecimal applyInterest(String accountNumber, String pin) 
            throws AccountPersistenceException, AuthenticationException {
        BankAccount account = getAccount(accountNumber, pin);
        
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalArgumentException("Only savings accounts earn interest");
        }

        BigDecimal interest = ((SavingsAccount) account).applyInterest();
        accountRepository.updateBalance(account);
        transactionRepository.logTransaction(
                accountNumber, 
                "INTEREST", 
                interest, 
                LocalDateTime.now()
        );
        return interest;
    }

    public void closeAccount(String accountNumber, String pin) 
            throws AccountPersistenceException, AuthenticationException {
        BankAccount account = getAccount(accountNumber, pin);
        accountRepository.delete(accountNumber);
        transactionRepository.logTransaction(
                accountNumber, 
                "ACCOUNT_CLOSED", 
                account.getBalance(), 
                LocalDateTime.now()
        );
    }
}