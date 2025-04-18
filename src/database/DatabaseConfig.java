package database;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Configuration loading failed", e);
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    public static BigDecimal getSavingsMinBalance() {
        return new BigDecimal(properties.getProperty("savings.min_balance"));
    }

    public static BigDecimal getSavingsMaxTransaction() {
        return new BigDecimal(properties.getProperty("savings.max_transaction"));
    }

    public static BigDecimal getSavingsInterestRate() {
        return new BigDecimal(properties.getProperty("savings.interest_rate"));
    }

    public static BigDecimal getCurrentOverdraftLimit() {
        return new BigDecimal(properties.getProperty("current.overdraft_limit"));
    }

    public static BigDecimal getCurrentMaxTransaction() {
        return new BigDecimal(properties.getProperty("current.max_transaction"));
    }
}