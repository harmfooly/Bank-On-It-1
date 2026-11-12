import java.io.*;
import java.util.*;

// ===================================================
// ===================================================
// CHECKING ACCOUNT CLASS 
// ===================================================
// ===================================================
// Implements a menu for User to check balance and make deposit/withrawal
public class CheckingAccount implements HasMenu, Serializable {

    static final Scanner in = new Scanner(System.in);
    private double balance;

    CheckingAccount() {
        this.balance = 0;
    } // end CheckingAccount constructor

    CheckingAccount(double balance) {
        this.balance = balance;
    } // end balance setter

    public static void main(String[] args) {
        Bank.main(args); // starts the program at the banking menu
    } // end CheckingAccount main

    String accountType() {
        return "Checking Account";
    } // end accountType

    public String menu() {
        System.out.println("\n(" + accountType() + ")");
        System.out.println("1. Check balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Back");
        System.out.print("Choice: ");
        return in.nextLine();
    } // end CheckingAccount Menu

    public void start() {
        String choice = menu();
        while (!choice.equals("4")) {
            chooseOption(choice);
            choice = menu();
        }
    }  // end startup for CheckingAccount

    // This is the template for all menu picking, handled as a choice String
    private void chooseOption(String choice) {
        if (choice.equals("1")) {
            checkBalance();
        } else if (choice.equals("2")) {
            makeDeposit();
        } else if (choice.equals("3")) {
            makeWithdrawal();
        } else {
            System.out.println("Invalid choice.");
        }
    }  // end chooseOption

    double getBalance() {
        return balance;
    } // end getBalance

    String getBalanceString() {
        return String.format("$%,.2f", balance);
    } // end getBalanceString

    void setBalance(double balance) {
        this.balance = balance;
    } // end setBalance

    void checkBalance() {
        System.out.println("Balance: " + getBalanceString());
    } // end checkBalance

    private double getDouble() {
        try {
            return Double.parseDouble(in.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    } // end getDouble

    void makeDeposit() {
        System.out.print("Deposit amount: ");
        double amount = getDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        balance += amount;
        System.out.println("Deposited. New balance: " + getBalanceString());
    } // end makeDeposit

    void makeWithdrawal() {
        System.out.print("Withdrawal amount: ");
        double amount = getDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= amount;
            System.out.println("Withdrawn. New balance: " + getBalanceString());
        }
    } // end makeWithdrawal
}  // end CheckingAccount class
