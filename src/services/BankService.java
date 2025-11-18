package services;

import models.*;
import exceptions.*;

import java.util.*;

public class BankService {
    private final Map<String, Account> accounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final Random random = new Random();

    // Generate a unique 10-digit account number as string
    private String generateUniqueAccountNumber() {
        String acc;
        do {
            long num = (long)(random.nextDouble() * 9_000_000_000L) + 1_000_000_000L; // 10-digit
            acc = String.valueOf(num);
        } while (accounts.containsKey(acc));
        return acc;
    }

    public Account createSavingsAccount(String owner, double initDeposit, double interestRate) {
        String accId = generateUniqueAccountNumber();
        Account acc = new SavingsAccount(accId, owner, initDeposit, interestRate);
        accounts.put(accId, acc);
        transactions.add(new Transaction(null, accId, initDeposit, Transaction.Type.DEPOSIT));
        return acc;
    }

    public Account createCurrentAccount(String owner, double initDeposit, double overdraftLimit) {
        String accId = generateUniqueAccountNumber();
        Account acc = new CurrentAccount(accId, owner, initDeposit, overdraftLimit);
        accounts.put(accId, acc);
        transactions.add(new Transaction(null, accId, initDeposit, Transaction.Type.DEPOSIT));
        return acc;
    }

    public Account getAccount(String accountId) throws AccountNotFoundException {
        if (accountId == null) throw new AccountNotFoundException("Account ID cannot be null");
        String id = accountId.trim();
        Account acc = accounts.get(id);
        if (acc == null) throw new AccountNotFoundException("Account not found: " + id);
        return acc;
    }

    public Collection<Account> listAllAccounts() {
        return accounts.values();
    }

    public void deposit(String toAccountId, double amount) throws AccountNotFoundException {
        Account to = getAccount(toAccountId);
        to.deposit(amount);
        transactions.add(new Transaction(null, toAccountId, amount, Transaction.Type.DEPOSIT));
    }

    public void withdraw(String fromAccountId, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account from = getAccount(fromAccountId);
        from.withdraw(amount);
        transactions.add(new Transaction(fromAccountId, null, amount, Transaction.Type.WITHDRAW));
    }

    public void transfer(String fromAccountId, String toAccountId, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account from = getAccount(fromAccountId);
        Account to = getAccount(toAccountId);
        from.withdraw(amount);
        to.deposit(amount);
        transactions.add(new Transaction(fromAccountId, toAccountId, amount, Transaction.Type.TRANSFER));
    }

    public List<Transaction> getTransactionsForAccount(String accountId) {
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : transactions) {
            if (accountId.equals(t.getFromAccountId()) || accountId.equals(t.getToAccountId())) {
                res.add(t);
            }
        }
        return res;
    }
}
