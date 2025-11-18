import models.*;
import services.BankService;
import exceptions.*;

import java.util.Scanner;


 class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final BankService bank = new BankService();

    public static void main(String[] args) {
        System.out.println("=== Smart Banking System ===");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> handleCreateAccount();
                    case "2" -> handleListAccounts();
                    case "3" -> handleDeposit();
                    case "4" -> handleWithdraw();
                    case "5" -> handleTransfer();
                    case "6" -> handleStatement();
                    case "0" -> { running = false; System.out.println("Goodbye!"); }
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Create Account (Savings/Current)");
        System.out.println("2. List Accounts");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer");
        System.out.println("6. Print Account Statement");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private static void handleCreateAccount() {
        System.out.print("Owner name: ");
        String owner = sc.nextLine().trim();
        System.out.print("Type (savings/current): ");
        String type = sc.nextLine().trim().toLowerCase();
        System.out.print("Initial deposit: ");
        double init = safeReadDouble();
        if (type.equals("savings")) {
            System.out.print("Interest rate (annual %): ");
            double rate = safeReadDouble();
            Account acc = bank.createSavingsAccount(owner, init, rate);
            System.out.println("\n=== ACCOUNT CREATED SUCCESSFULLY ===");
            System.out.println(acc);
        } else {
            System.out.print("Overdraft limit: ");
            double od = safeReadDouble();
            Account acc = bank.createCurrentAccount(owner, init, od);
            System.out.println("\n=== ACCOUNT CREATED SUCCESSFULLY ===");
            System.out.println(acc);
        }
    }

    private static void handleListAccounts() {
        System.out.println("Accounts:");
        for (Account a : bank.listAllAccounts()) {
            System.out.println("---------");
            System.out.println(a);
        }
    }

    private static void handleDeposit() throws AccountNotFoundException {
        System.out.print("Account Number: ");
        String id = sc.nextLine().trim();
        System.out.print("Amount: ");
        double amt = safeReadDouble();
        bank.deposit(id, amt);
        System.out.println("Deposited " + amt);
    }

    private static void handleWithdraw() throws AccountNotFoundException, InsufficientFundsException {
        System.out.print("Account Number: ");
        String id = sc.nextLine().trim();
        System.out.print("Amount: ");
        double amt = safeReadDouble();
        bank.withdraw(id, amt);
        System.out.println("Withdrawn " + amt);
    }

    private static void handleTransfer() throws AccountNotFoundException, InsufficientFundsException {
        System.out.print("From Account Number: ");
        String from = sc.nextLine().trim();
        System.out.print("To Account Number: ");
        String to = sc.nextLine().trim();
        System.out.print("Amount: ");
        double amt = safeReadDouble();
        bank.transfer(from, to, amt);
        System.out.println("Transferred " + amt);
    }

    private static void handleStatement() {
        System.out.print("Account Number: ");
        String id = sc.nextLine().trim();
        try {
            Account acc = bank.getAccount(id);
            System.out.println(acc);
            System.out.println("Transactions:");
            for (models.Transaction t : bank.getTransactionsForAccount(id)) {
                System.out.println(t);
            }
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static double safeReadDouble() {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Enter again: ");
            }
        }
    }
}
