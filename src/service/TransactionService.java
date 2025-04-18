package service;

import model.Transaction;
import model.Transfer;
import exceptions.AccountPersistenceException;
import repository.TransactionRepository;

import java.util.List;

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAccountTransactions(String accountNumber, int limit) 
            throws AccountPersistenceException {
        return transactionRepository.getRecentTransactions(accountNumber);
    }

    public List<Transfer> getAccountTransfers(String accountNumber, int limit) 
            throws AccountPersistenceException {
        return transactionRepository.getRecentTransfers(accountNumber);
    }

    public List<Transaction> getAllTransactions() throws AccountPersistenceException {
        // Implementation would query all transactions (paginated in real application)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<Transfer> getAllTransfers() throws AccountPersistenceException {
        // Implementation would query all transfers (paginated in real application)
        throw new UnsupportedOperationException("Not implemented yet");
    }
}