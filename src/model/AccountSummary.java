package model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Summary view of an account with recent transactions
 */
public class AccountSummary {
    private AccountDTO account;
    private BigDecimal currentBalance;
    private List<Transaction> recentTransactions;
    private int transactionCount;

    // Constructors
    public AccountSummary() {}

    public AccountSummary(AccountDTO account, BigDecimal currentBalance, 
                         List<Transaction> recentTransactions, int transactionCount) {
        this.account = account;
        this.currentBalance = currentBalance;
        this.recentTransactions = recentTransactions;
        this.transactionCount = transactionCount;
    }

    // Getters and Setters
    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<Transaction> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<Transaction> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    @Override
    public String toString() {
        return "AccountSummary{" +
                "account=" + account +
                ", currentBalance=" + currentBalance +
                ", recentTransactions=" + recentTransactions +
                ", transactionCount=" + transactionCount +
                '}';
    }
}