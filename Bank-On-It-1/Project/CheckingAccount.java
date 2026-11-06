import java.io.Serializable;
import java.util.*;

interface HasMenu {
    String menu();
    void start();
}

public class CheckingAccount implements HasMenu, Serializable {
    static final Scanner in = new Scanner(System.in);
    private double balance;

    CheckingAccount() {
        this.balance = 0;
    }

    CheckingAccount(double balance) {
        this.balance = balance;
    }

    public static void main(String[] args) {
        Customer.main(args);
    }

    String accountType() {
        return "Checking Account";
    }

    public String menu() {
        System.out.println("\n(" + accountType() + ")");
        System.out.println("1. Check balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Back");
        System.out.print("Choice: ");
        return in.nextLine();
    }

    public void start() {
        if (balance < 0) {
            System.out.println("Your account is overdrawn. Please deposit funds.");
        }

        String choice = menu();
        while (!choice.equals("4")) {
            processChoice(choice);
            choice = menu();
        }
    }

    private void processChoice(String choice) {
        if (choice.equals("1")) {
            checkBalance();
        } else if (choice.equals("2")) {
            makeDeposit();
        } else if (choice.equals("3")) {
            makeWithdrawal();
        } else if (choice.equals("4")) {
            System.out.println("Returning to previous menu...");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    double getBalance() {
        return balance;
    }

    String getBalanceString() {
        return String.format("$%,.2f", balance);
    }

    void setBalance(double balance) {
        this.balance = balance;
    }

    void checkBalance() {
        System.out.println("Balance: " + getBalanceString());
    }

    private double getDouble() {
        try {
            return Double.parseDouble(in.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    void makeDeposit() {
        System.out.print("Deposit amount: ");
        double amount = getDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        balance += amount;
        System.out.println("Deposited. New balance: " + getBalanceString());
    }

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
    }
}

class SavingsAccount extends CheckingAccount {
    double interestRate;

    SavingsAccount() {
        this(0, 0.02);
    }

    SavingsAccount(double balance, double interestRate) {
        super(balance);
        setInterestRate(interestRate);
    }

    public static void main(String[] args) {
        new SavingsAccount().start();
    }

    @Override
    String accountType() {
        return "Savings Account";
    }

    // package-private and not in the menu, so customers can't call it directly
    void calcInterest() {
        setBalance(getBalance() + getBalance() * interestRate);
    }

    void setInterestRate(double interestRate) {
        if (interestRate >= 0) {
            this.interestRate = interestRate;
        }
    }

    double getInterestRate() {
        return interestRate;
    }
}

abstract class User implements HasMenu, Serializable {
    String userName;
    String PIN;

    boolean login() {
        System.out.print("Username (or type 'none' to create a new login): ");
        String name = CheckingAccount.in.nextLine().trim();
        if (name.equalsIgnoreCase("none")) {
            System.out.print("New username: ");
            name = CheckingAccount.in.nextLine().trim();
            System.out.print("New PIN: ");
            String pin = CheckingAccount.in.nextLine().trim();
            setUserName(name);
            setPIN(pin);
            return true;
        }
        System.out.print("PIN:");
        String pin = CheckingAccount.in.nextLine().trim();
        return login(name, pin);
    }

    boolean login(String userName, String PIN) {
        return this.userName.equals(userName) && this.PIN.equals(PIN);
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return userName;
    }

    void setPIN(String PIN) {
        this.PIN = PIN;
    }

    String getPIN() {
        return PIN;
    }

    abstract String getReport();
}

class Customer extends User {
    CheckingAccount checking;
    SavingsAccount savings;

    public static void main(String[] args) {
        Customer c = new Customer("antonio", "2222");
        if (c.login()) {
            c.start();
        } else {
            System.out.println("Login failed.");
        }
    }

    Customer() {
        this("customer", "0000");
    }

    Customer(String userName, String PIN) {
        this.userName = userName;
        this.PIN = PIN;
        checking = new CheckingAccount();
        savings = new SavingsAccount();
    }

    public String menu() {
        System.out.println("\nWelcome, " + userName.toUpperCase() + "!");
        System.out.println("1. Checking account");
        System.out.println("2. Savings account");
        System.out.println("3. Change PIN");
        System.out.println("4. Account report");
        System.out.println("5. Log out");
        System.out.print("Choice: ");
        return CheckingAccount.in.nextLine().trim();
    }

    public void start() {
        String choice = menu();
        while (!choice.equals("5")) {
            processChoice(choice);
            choice = menu();
        }
        System.out.println("Logging out...");
    }

    private void processChoice(String choice) {
        if (choice.equals("1")) {
            checking.start();
        } else if (choice.equals("2")) {
            savings.start();
        } else if (choice.equals("3")) {
            setPin();
        } else if (choice.equals("4")) {
            System.out.println(getReport());
        } else {
            System.out.println("Invalid choice.");
        }
    }

    void setPin() {
        System.out.print("New PIN: ");
        String pin = CheckingAccount.in.nextLine();
        if (pin.length() != 4) {
            System.out.println("PIN must be at least 4 characters/digits long. Mix of letters and numbers is recommended.");
            return;
        }
        setPIN(pin);
        System.out.println("PIN updated.");
    }

    String getReport() {
        return "Report for " + userName.toUpperCase()
                + "\nChecking balance: " + checking.getBalanceString()
                + "\nSavings balance:  " + savings.getBalanceString()
                + "\nSavings interest rate: " + String.format("%.2f%%", savings.getInterestRate() * 100);
    }
}