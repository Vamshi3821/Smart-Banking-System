package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type { DEPOSIT, WITHDRAW, TRANSFER }

    private final String txId;
    private final String fromAccountId; // may be null for deposit
    private final String toAccountId;   // may be null for withdraw
    private final double amount;
    private final Type type;
    private final LocalDateTime timestamp;

    public Transaction(String fromAccountId, String toAccountId, double amount, Type type) {
        this.txId = UUID.randomUUID().toString();
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getTxId() { return txId; }
    public String getFromAccountId() { return fromAccountId; }
    public String getToAccountId() { return toAccountId; }
    public double getAmount() { return amount; }
    public Type getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        String parties;
        switch (type) {
            case DEPOSIT -> parties = "-> " + (toAccountId == null ? "?" : toAccountId);
            case WITHDRAW -> parties = (fromAccountId == null ? "?" : fromAccountId) + " ->";
            case TRANSFER -> parties = (fromAccountId == null ? "?" : fromAccountId) + " -> " + (toAccountId == null ? "?" : toAccountId);
            default -> parties = "";
        }
        return String.format("[%s] %s %s : %.2f at %s", txId.substring(0,8), type, parties, amount, timestamp);
    }
}
