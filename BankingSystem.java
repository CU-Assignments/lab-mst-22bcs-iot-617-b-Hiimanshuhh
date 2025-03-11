import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.InputMismatchException;

class BankAccount {
    private String name;
    private String accountNumber;
    private double balance;

    public BankAccount(String name, String accountNumber, double initialBalance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be positive. Transaction cancelled.");
            return;
        }

        balance += amount;
        System.out.println("Deposit successful! Current Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive. Transaction cancelled.");
            return;
        }

        if (amount > balance) {
            System.out.println("Error: Insufficient funds. Current Balance: " + balance);
            return;
        }

        balance -= amount;
        System.out.println("Withdrawal successful! Current Balance: " + balance);
    }

    @Override
    public String toString() {
        return "Account Information:\nName: " + name + "\nAccount Number: " + accountNumber + "\nBalance: " + balance;
    }
}

class Bank {
    private Map<String, BankAccount> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void createAccount(String name, String accountNumber, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Error: Account number already exists. Please choose a different account number.");
            return;
        }

        if (initialBalance < 0) {
            System.out.println("Error: Initial balance cannot be negative.");
            return;
        }

        BankAccount account = new BankAccount(name, accountNumber, initialBalance);
        accounts.put(accountNumber, account);
        System.out.println("Account created successfully!");
        System.out.println(account);
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

public class BankingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Banking System Menu ====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = getValidChoice();

            switch (choice) {
                case 1:
                    createAccountMenu();
                    break;

                case 2:
                    depositMenu();
                    break;

                case 3:
                    withdrawMenu();
                    break;

                case 4:
                    checkBalanceMenu();
                    break;

                case 5:
                    running = false;
                    System.out.println("Thank you for using the Banking System!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1-5.");
            }
        }

        scanner.close();
    }

    private static int getValidChoice() {
        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1-5.");
                System.out.print("Enter your choice (1-5): ");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Consume newline
        return choice;
    }

    private static double getValidAmount(String prompt) {
        double amount = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);
            try {
                amount = scanner.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Consume newline
        return amount;
    }

    private static void createAccountMenu() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        double initialBalance = getValidAmount("Enter Initial Balance: ");

        bank.createAccount(name, accountNumber, initialBalance);
    }

    private static void depositMenu() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = bank.getAccount(accountNumber);

        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        double amount = getValidAmount("Enter Deposit Amount: ");
        account.deposit(amount);
    }

    private static void withdrawMenu() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = bank.getAccount(accountNumber);

        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        double amount = getValidAmount("Enter Withdrawal Amount: ");
        account.withdraw(amount);
    }

    private static void checkBalanceMenu() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = bank.getAccount(accountNumber);

        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        System.out.println(account);
    }
}