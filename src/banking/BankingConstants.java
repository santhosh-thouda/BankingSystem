package banking;

import java.math.BigDecimal;

/**
 * Constants used throughout the banking package
 */
public final class BankingConstants {
    public static final BigDecimal SAVINGS_MIN_BALANCE = new BigDecimal("1000.00");
    public static final BigDecimal SAVINGS_MAX_TRANSACTION = new BigDecimal("100000.00");
    public static final BigDecimal SAVINGS_INTEREST_RATE = new BigDecimal("0.04");
    
    public static final BigDecimal CURRENT_OVERDRAFT_LIMIT = new BigDecimal("-10000.00");
    public static final BigDecimal CURRENT_MAX_TRANSACTION = new BigDecimal("200000.00");
    
    public static final int PIN_LENGTH = 4;
    public static final int ACCOUNT_NUMBER_LENGTH = 10;
    
    private BankingConstants() {
        // Private constructor to prevent instantiation
    }
}