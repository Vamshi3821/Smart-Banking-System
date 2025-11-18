package models;

import exceptions.InsufficientFundsException;

public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final double overdraftLimit;

    public CurrentAccount(String accountId, String ownerName, double initialDeposit, double overdraftLimit) {
        super(accountId, ownerName, initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw amount must be positive");
        if (amount > balance + overdraftLimit) throw new InsufficientFundsException("Exceeds overdraft limit");
        balance -= amount;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public String toString() {
        return "CurrentAccount - " + super.toString() + String.format("\nOverdraft Limit: %.2f", overdraftLimit);
    }
}
