package repository;

import database.DatabaseConnection;
import exceptions.AccountPersistenceException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class TransactionRepository {
    public void logTransaction(String accountNumber, String type, 
                             BigDecimal amount, LocalDateTime date) 
                             throws AccountPersistenceException {
        String sql = "INSERT INTO transactions (accountNumber, transactionType, amount) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, accountNumber);
            stmt.setString(2, type);
            stmt.setBigDecimal(3, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AccountPersistenceException("Transaction logging failed", e);
        }
    }
}