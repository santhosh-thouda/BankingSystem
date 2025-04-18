package repository;

import banking.BankAccount;
import banking.CurrentAccount;
import banking.SavingsAccount;
import database.DatabaseConnection;
import exceptions.AccountPersistenceException;
import model.AccountDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private static final String SAVE_ACCOUNT_SQL = 
        "INSERT INTO accounts (accountNumber, accountHolderName, pin, balance, accountType) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_NUMBER_SQL = 
        "SELECT * FROM accounts WHERE accountNumber = ?";
    private static final String UPDATE_BALANCE_SQL = 
        "UPDATE accounts SET balance = ? WHERE accountNumber = ?";
    private static final String DELETE_ACCOUNT_SQL = 
        "DELETE FROM accounts WHERE accountNumber = ?";
    private static final String ACCOUNT_EXISTS_SQL = 
        "SELECT 1 FROM accounts WHERE accountNumber = ?";
    private static final String FIND_ALL_SQL = 
        "SELECT * FROM accounts";

    public void save(BankAccount account) throws AccountPersistenceException {
        String sql = "INSERT INTO accounts (accountNumber, accountHolderName, pin, balance, accountType) " +
                   "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getAccountHolderName());
            stmt.setString(3, account.getPin());
            stmt.setBigDecimal(4, account.getBalance());
            stmt.setString(5, account.getAccountType());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed SQL: " + sql);
            throw new AccountPersistenceException("Failed to save account: " + e.getMessage(), e);
        }
    }

    public BankAccount findByAccountNumber(String accountNumber) throws AccountPersistenceException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_NUMBER_SQL)) {
            
            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccount(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new AccountPersistenceException("Failed to find account: " + e.getMessage(), e);
        }
    }

    public void updateBalance(BankAccount account) throws AccountPersistenceException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_BALANCE_SQL)) {
            
            stmt.setBigDecimal(1, account.getBalance());
            stmt.setString(2, account.getAccountNumber());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new AccountPersistenceException("No account found with number: " + account.getAccountNumber());
            }
        } catch (SQLException e) {
            throw new AccountPersistenceException("Failed to update account balance: " + e.getMessage(), e);
        }
    }

    public void delete(String accountNumber) throws AccountPersistenceException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_ACCOUNT_SQL)) {
            
            stmt.setString(1, accountNumber);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new AccountPersistenceException("No account found with number: " + accountNumber);
            }
        } catch (SQLException e) {
            throw new AccountPersistenceException("Failed to delete account: " + e.getMessage(), e);
        }
    }

    public boolean accountExists(String accountNumber) throws AccountPersistenceException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ACCOUNT_EXISTS_SQL)) {
            
            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new AccountPersistenceException("Failed to check account existence: " + e.getMessage(), e);
        }
    }

    public List<AccountDTO> findAllAccounts() throws AccountPersistenceException {
        List<AccountDTO> accounts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_SQL)) {
            
            while (rs.next()) {
                accounts.add(mapRowToAccountDTO(rs));
            }
            return accounts;
        } catch (SQLException e) {
            throw new AccountPersistenceException("Failed to retrieve all accounts: " + e.getMessage(), e);
        }
    }

    private BankAccount mapRowToAccount(ResultSet rs) throws SQLException {
        String accountNumber = rs.getString("accountNumber");
        String accountHolderName = rs.getString("accountHolderName");
        String pin = rs.getString("pin");
        BigDecimal balance = rs.getBigDecimal("balance");
        String accountType = rs.getString("accountType");
        
        return accountType.equals("SAVINGS") ? 
            new SavingsAccount(accountNumber, accountHolderName, pin, balance) :
            new CurrentAccount(accountNumber, accountHolderName, pin, balance);
    }

    private AccountDTO mapRowToAccountDTO(ResultSet rs) throws SQLException {
        return new AccountDTO(
            rs.getString("accountNumber"),
            rs.getString("accountHolderName"),
            rs.getString("accountType"),
            rs.getBigDecimal("balance"),
            rs.getTimestamp("createdAt").toLocalDateTime()
        );
    }
}