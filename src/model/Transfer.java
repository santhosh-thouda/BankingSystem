package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a funds transfer between accounts
 */
public class Transfer {
    private Long transferId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private LocalDateTime transferDate;

    // Constructors
    public Transfer() {}

    public Transfer(Long transferId, String fromAccount, String toAccount, 
                   BigDecimal amount, LocalDateTime transferDate) {
        this.transferId = transferId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transferDate = transferDate;
    }

    // Getters and Setters
    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", amount=" + amount +
                ", transferDate=" + transferDate +
                '}';
    }
}