package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            createTables(stmt);
            System.out.println("Database initialized successfully");
        }
    }

    private static void createTables(Statement stmt) throws SQLException {
        stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
            "accountNumber VARCHAR(20) PRIMARY KEY, " +
            "accountHolderName VARCHAR(100) NOT NULL, " +
            "pin VARCHAR(4) NOT NULL, " +
            "balance DECIMAL(15,2) NOT NULL, " +
            "accountType VARCHAR(20) NOT NULL, " +
            "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            
        stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
            "transactionId BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "accountNumber VARCHAR(20) NOT NULL, " +
            "transactionType VARCHAR(20) NOT NULL, " +
            "amount DECIMAL(15,2) NOT NULL, " +
            "transactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber))");
            
        stmt.execute("CREATE TABLE IF NOT EXISTS transfers (" +
            "transferId BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "fromAccount VARCHAR(20) NOT NULL, " +
            "toAccount VARCHAR(20) NOT NULL, " +
            "amount DECIMAL(15,2) NOT NULL, " +
            "transferDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (fromAccount) REFERENCES accounts(accountNumber), " +
            "FOREIGN KEY (toAccount) REFERENCES accounts(accountNumber))");
    }

    public static void cleanDatabase() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("DROP TABLE IF EXISTS transfers");
            stmt.execute("DROP TABLE IF EXISTS transactions");
            stmt.execute("DROP TABLE IF EXISTS accounts");
        }
    }
}