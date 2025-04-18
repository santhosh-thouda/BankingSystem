package util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for generating unique account numbers
 */
public final class AccountNumberGenerator {
    private static final AtomicLong COUNTER = new AtomicLong(1000000000L);
    private static final String BANK_CODE = "22";

    private AccountNumberGenerator() {
        // Private constructor to prevent instantiation
    }

    public static synchronized String generateAccountNumber() {
        long nextNum = COUNTER.getAndIncrement();
        String baseNumber = BANK_CODE + String.format("%08d", nextNum);
        return baseNumber + calculateCheckDigit(baseNumber);
    }

    private static int calculateCheckDigit(String number) {
        int sum = 0;
        boolean alternate = true;
        
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        
        return (10 - (sum % 10)) % 10;
    }
}