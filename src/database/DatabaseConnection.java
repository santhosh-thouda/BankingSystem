package database;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;

public class DatabaseConnection {
    private static final Properties properties = new Properties();
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (FileInputStream input = new FileInputStream("config.properties")) {
                properties.load(input);
            }
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            properties.getProperty("db.url"),
            properties.getProperty("db.username"),
            properties.getProperty("db.password")
        );
    }
}