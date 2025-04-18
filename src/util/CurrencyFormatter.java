package util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class for formatting currency values
 */
public final class CurrencyFormatter {
    private static final NumberFormat CURRENCY_FORMATTER = 
        NumberFormat.getCurrencyInstance(Locale.US);

    private CurrencyFormatter() {
        // Private constructor to prevent instantiation
    }

    public static String format(BigDecimal amount) {
        if (amount == null) {
            return "$0.00";
        }
        return CURRENCY_FORMATTER.format(amount);
    }

    public static String formatWithSymbol(BigDecimal amount, String symbol) {
        if (amount == null) {
            return symbol + "0.00";
        }
        return symbol + String.format("%,.2f", amount);
    }

    public static BigDecimal parse(String amountStr) throws NumberFormatException {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            throw new NumberFormatException("Empty amount");
        }
        try {
            // Remove any currency symbols and commas
            String cleaned = amountStr.replaceAll("[^\\d.]", "");
            return new BigDecimal(cleaned);
        } catch (Exception e) {
            throw new NumberFormatException("Invalid amount format");
        }
    }
}