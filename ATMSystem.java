import java.util.Scanner;

/**
 * Class representing a user's bank account.
 * It manages the balance and provides methods for transactions.
 */
class BankAccount {
    private double balance;

    /**
     * Initializes the account with a starting balance.
     * @param initialBalance The starting amount in the account.
     */
    public BankAccount(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            // Start with 0 if an invalid initial balance is provided
            this.balance = 0;
            System.out.println("Warning: Initial balance must be non-negative. Set to 0.00.");
        }
    }

    /**
     * Returns the current account balance.
     * @return The current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Deposits a specified amount into the account.
     * @param amount The amount to deposit.
     * @return true if deposit was successful, false otherwise.
     */
    public boolean deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    /**
     * Withdraws a specified amount from the account.
     * Includes validation to ensure sufficient funds.
     * @param amount The amount to withdraw.
     * @return true if withdrawal was successful, false otherwise.
     */
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}

/**
 * Main class representing the ATM machine and its user interface.
 * It interacts with the BankAccount class to perform transactions.
 */
public class ATMSystem {
    private BankAccount account;
    private Scanner scanner;

    /**
     * Constructor to initialize the ATM with a linked BankAccount and a Scanner for input.
     * @param account The bank account associated with this ATM session.
     */
    public ATMSystem(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu options to the user.
     */
    private void displayMenu() {
        System.out.println("\n--- ATM Services Menu ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.print("Please enter your choice (1-4): ");
    }

    /**
     * Handles the user's balance inquiry.
     */
    private void checkBalance() {
        System.out.printf("\nYour current balance is: $%.2f%n", account.getBalance());
    }

    /**
     * Handles the deposit transaction logic.
     */
    private void handleDeposit() {
        try {
            System.out.print("Enter amount to deposit: $");
            double amount = scanner.nextDouble();

            if (amount <= 0) {
                System.out.println("Transaction Failed: Deposit amount must be positive.");
                return;
            }

            if (account.deposit(amount)) {
                System.out.printf("Success! $%.2f deposited. New balance: $%.2f%n", amount, account.getBalance());
            } else {
                // Should not happen if amount > 0 check is above, but serves as a fail-safe
                System.out.println("Transaction Failed: Could not process deposit.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid Input: Please enter a valid number.");
            scanner.next(); // Clear the invalid input
        }
    }

    /**
     * Handles the withdrawal transaction logic, including balance validation.
     */
    private void handleWithdrawal() {
        try {
            System.out.print("Enter amount to withdraw: $");
            double amount = scanner.nextDouble();

            if (amount <= 0) {
                System.out.println("Transaction Failed: Withdrawal amount must be positive.");
                return;
            }

            if (account.withdraw(amount)) {
                System.out.printf("Success! $%.2f withdrawn. New balance: $%.2f%n", amount, account.getBalance());
            } else if (amount > account.getBalance()) {
                System.out.println("Transaction Failed: Insufficient funds.");
            } else {
                System.out.println("Transaction Failed: Could not process withdrawal.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid Input: Please enter a valid number.");
            scanner.next(); // Clear the invalid input
        }
    }

    /**
     * Starts the main ATM interaction loop.
     */
    public void start() {
        System.out.println("Welcome to the Gemini Virtual ATM!");
        boolean running = true;

        while (running) {
            displayMenu();

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        handleDeposit();
                        break;
                    case 3:
                        handleWithdrawal();
                        break;
                    case 4:
                        running = false;
                        System.out.println("\nThank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select an option between 1 and 4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number for your choice.");
                scanner.next(); // Clear the invalid input (non-integer)
            }
        }
        scanner.close();
    }

    /**
     * Main method to set up and run the ATM simulation.
     */
    public static void main(String[] args) {
        // 1. Create the user's bank account with an initial balance
        BankAccount userAccount = new BankAccount(500.00); // Starting with $500.00

        // 2. Create the ATM machine and connect it to the account
        ATMSystem atm = new ATMSystem(userAccount);

        // 3. Start the interactive session
        atm.start();
    }
}
