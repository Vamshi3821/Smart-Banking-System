package models;

import exceptions.InsufficientFundsException;

public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final double interestRate;

    public SavingsAccount(String accountId, String ownerName, double initialDeposit, double interestRate) {
        super(accountId, ownerName, initialDeposit);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw amount must be positive");
        if (amount > balance) throw new InsufficientFundsException("Insufficient funds in savings account");
        balance -= amount;
    }

    public double applyAnnualInterest() {
        double interest = balance * (interestRate / 100.0);
        balance += interest;
        return interest;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return "SavingsAccount - " + super.toString() + String.format("\nInterest Rate: %.2f%%", interestRate);
    }
}
