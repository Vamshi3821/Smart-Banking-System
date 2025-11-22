package models;

import java.io.Serializable;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final String accountId;
    protected final String ownerName;
    protected double balance;

    // Note: accountId is provided by BankService to ensure uniqueness..
    public Account(String accountId, String ownerName, double initialDeposit) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = initialDeposit;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive");
        balance += amount;
    }

    public abstract void withdraw(double amount) throws exceptions.InsufficientFundsException;

    @Override
    public String toString() {
        return String.format("Account Number: %s\nOwner: %s\nBalance: %.2f", accountId, ownerName, balance);
    }
}
