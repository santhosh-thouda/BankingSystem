package util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Utility class for validating user inputs
 */
public final class InputValidator {
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s'-]{2,100}$");
    private static final Pattern PIN_PATTERN = Pattern.compile("^\\d{4}$");
    
    private InputValidator() {
        // Private constructor to prevent instantiation
    }

    public static void validateAccountNumber(String accountNumber) {
        if (accountNumber == null || !ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches()) {
            throw new IllegalArgumentException(
                "Account number must be exactly 10 digits\nExample: 1234567890"
            );
        }
    }

    public static void validateName(String name) {
        if (name == null || !NAME_PATTERN.matcher(name.trim()).matches()) {
            throw new IllegalArgumentException(
                "Name must be 2-100 characters long and contain only letters, spaces, hyphens, and apostrophes"
            );
        }
    }

    public static void validateAccountType(String type) {
        if (type == null || 
            (!type.equalsIgnoreCase("SAVINGS") && !type.equalsIgnoreCase("CURRENT"))) {
            throw new IllegalArgumentException(
                "Account type must be either SAVINGS or CURRENT\nExample: SAVINGS"
            );
        }
    }

    public static void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "Amount must be positive\nExample: 100.00"
            );
        }
    }

    public static void validatePin(String pin) {
        if (pin == null || !PIN_PATTERN.matcher(pin).matches()) {
            throw new IllegalArgumentException(
                "PIN must be exactly 4 digits\nExample: 1234"
            );
        }
    }

    public static void validateTransactionType(String type) {
        if (type == null || !(type.equals("DEPOSIT") || type.equals("WITHDRAWAL") || 
                             type.equals("TRANSFER") || type.equals("INTEREST"))) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
    }
}