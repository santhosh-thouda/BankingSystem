package repository;

/**
 * Factory for creating repository instances
 */
public class RepositoryFactory {
    private static AccountRepository accountRepository;
    private static TransactionRepository transactionRepository;

    public static synchronized AccountRepository getAccountRepository() {
        if (accountRepository == null) {
            accountRepository = new AccountRepository();
        }
        return accountRepository;
    }

    public static synchronized TransactionRepository getTransactionRepository() {
        if (transactionRepository == null) {
            transactionRepository = new TransactionRepository();
        }
        return transactionRepository;
    }

    // For testing purposes
    public static void setAccountRepository(AccountRepository mockRepository) {
        accountRepository = mockRepository;
    }

    public static void setTransactionRepository(TransactionRepository mockRepository) {
        transactionRepository = mockRepository;
    }
}