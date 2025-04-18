package service;

import repository.AccountRepository;
import repository.TransactionRepository;
import repository.RepositoryFactory;

public class ServiceFactory {
    private static AccountService accountService;
    private static TransactionService transactionService;
    private static ReportingService reportingService;

    public static synchronized AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(
                RepositoryFactory.getAccountRepository(),
                RepositoryFactory.getTransactionRepository()
            );
        }
        return accountService;
    }

    public static synchronized TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionService(
                RepositoryFactory.getTransactionRepository()
            );
        }
        return transactionService;
    }

    public static synchronized ReportingService getReportingService() {
        if (reportingService == null) {
            reportingService = new ReportingService(
                RepositoryFactory.getAccountRepository(),
                RepositoryFactory.getTransactionRepository()
            );
        }
        return reportingService;
    }

    // For testing purposes
    public static void setAccountService(AccountService mockService) {
        accountService = mockService;
    }

    public static void setTransactionService(TransactionService mockService) {
        transactionService = mockService;
    }

    public static void setReportingService(ReportingService mockService) {
        reportingService = mockService;
    }
}