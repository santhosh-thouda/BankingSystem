package service;

import model.AccountSummary;
import exceptions.AccountPersistenceException;
import repository.AccountRepository;
import repository.TransactionRepository;

import java.math.BigDecimal;

public class ReportingService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ReportingService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getTotalBankBalance() throws AccountPersistenceException {
        // Implementation would sum all account balances
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public AccountSummary getAccountSummary(String accountNumber) 
            throws AccountPersistenceException {
        // Implementation would create comprehensive account summary
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String generateMonthlyStatement(String accountNumber) 
            throws AccountPersistenceException {
        // Implementation would generate formatted statement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}