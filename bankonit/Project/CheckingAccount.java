import java.io.*;
import java.util.*;

// ===================================================
// ===================================================
// HAS MENU INTERFACE
// ===================================================
// ===================================================
// Setup for menu and startup that will be used throughout project
interface HasMenu {
    String menu();
    void start();
} // end HasMenu

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

// ===================================================
// ===================================================
// SAVINGS ACCOUNT CLASS - Short clone of CheckingAccount 
// ===================================================
// ===================================================

class SavingsAccount extends CheckingAccount implements Serializable {

    double interestRate; // setup Interest rate variable

    SavingsAccount() {
        this(0, 0.02);
    }  // end SavingsAccount constructor

    SavingsAccount(double balance, double interestRate) {
        super(balance);
        setInterestRate(interestRate);
    }  // end SavingsAccount balance and interestRate setup

    public static void main(String[] args) {
        new SavingsAccount().start();
    }  // end main

    String accountType() {
        return "Savings Account";
    }  // end accountType

    // package is private and not in the menu, so customers can't call it directly
    void calcInterest() {
        setBalance(getBalance() + getBalance() * interestRate);
    }  // end calcInterest

    void setInterestRate(double interestRate) {
        if (interestRate >= 0) { // atempt to avoid negative interest rates
            this.interestRate = interestRate;
        }
    }  // end setInterestRate

    double getInterestRate() {
        return interestRate;
    }  // end getInterestRate
} // end SavingsAccount class

// ===================================================
// ===================================================
// USER CLASS
// ===================================================
// ===================================================
// Login logic, creation of new users
abstract class User implements HasMenu, Serializable {

    String userName;
    String PIN;

    boolean login() { // this will be the setup for logging in and/or creating new user
        System.out.print("Username (or type 'none' to create a new login): ");
        String name = CheckingAccount.in.nextLine();
        if (name.equalsIgnoreCase("none")) {
            System.out.print("New username: ");
            name = CheckingAccount.in.nextLine();
            System.out.print("New PIN: ");
            String pin = CheckingAccount.in.nextLine();
            setPIN(pin);
            setUserName(name);
            return true;
        }
        System.out.print("PIN: ");
        String pin = CheckingAccount.in.nextLine();
        return login(name, pin);
    } // end login boolean logic

    boolean login(String userName, String PIN) { // this exists just to ensure the userName and PIN are valid
        return this.userName.equals(userName) && this.PIN.equals(PIN);
    } // end login validation

    void setUserName(String userName) {
        this.userName = userName;
    } // end setUserName

    String getUserName() {
        return userName;
    }   // end getUserName

    void setPIN(String PIN) {
        this.PIN = PIN;
    }  // end setPIN

    String getPIN() {
        return PIN;
    }  // end getPIN

    abstract String getReport(); // getReport will be useful for customer information
} // end User class

// ===================================================
// ===================================================
// CUSTOMER CLASS - A customer has a checking account and a savings account
// ===================================================
// ===================================================
// Handles CheckingAccount, SavingsAccount, Customer information
class Customer extends User implements Serializable {

    CheckingAccount checking;
    SavingsAccount savings;

    Customer() {
        this("customer", "0000");
    } // end customer constructor

    Customer(String userName, String PIN) {
        this.userName = userName;
        setPIN(PIN);
        checking = new CheckingAccount();
        savings = new SavingsAccount();
    } // end customer sensitive info handling

    public static void main(String[] args) {
        Customer c = new Customer("antonio", "2222");

        while (!c.login()) {
            System.out.println("Login failed. Try again.");
        }
        c.start();
    } // end Customer main

    public String menu() {
        System.out.println("\nWelcome, " + userName.toUpperCase() + "!");
        System.out.println("1. Checking account");
        System.out.println("2. Savings account");
        System.out.println("3. Change PIN");
        System.out.println("4. Account report");
        System.out.println("5. Log out");
        System.out.print("Choice: ");
        return CheckingAccount.in.nextLine();
    } // end Customer menu

    public void start() {
        String choice = menu();
        while (!choice.equals("5")) {
            chooseOption(choice);
            choice = menu();
        }
        System.out.println("Logging out...");
    } // end Customer startup

    private void chooseOption(String choice) {
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
    } // end chooseOption

    void setPin() { // setting new pin 
        System.out.print("New PIN: ");
        String pin = CheckingAccount.in.nextLine();
        if (pin.isEmpty()) { // don't accept empty pins to avoid security issues.
            System.out.println("PIN cannot be empty. PIN not changed.");
            return;
        }
        setPIN(pin);
        System.out.println("PIN updated successfully.");
    } // end setPIN

    String getReport() { // this will take a basic report of the user's name, balances and interest rate(s)
        return "Report for " + userName.toUpperCase()
                + "\nChecking balance: " + checking.getBalanceString()
                + "\nSavings balance:  " + savings.getBalanceString()
                + "\nSavings interest rate: " + String.format("%.2f%%", savings.getInterestRate() * 100);
    } // end getReport
} // end Customer class

// ===================================================
// ===================================================
// BANK CLASS - A bank has an arrayList of customers and an administrator
// ===================================================
// ===================================================
// Handles loading and saving customers, the starting bank menu that branches 
// into customer/admin logins, or exiting the program entirely. This is the starting screen.

class Bank implements HasMenu, Serializable {
    private static final String FILE_NAME = "customers.ser";

    ArrayList<Customer> customers;
    Admin admin;

    Bank() {
        customers = new ArrayList<>();
        admin = new Admin();
    } // end Bank constructor
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.loadCustomers(bank.customers);
        bank.start();
        bank.saveCustomers(bank.customers);
    } // end Bank main

    public String menu() {
        System.out.println("\nBank Menu");
        System.out.println("1. Customer login");
        System.out.println("2. Administrator login");
        System.out.println("3. Exit");
        System.out.print("Choice: ");
        return CheckingAccount.in.nextLine();
    } // end Bank menu

    public void start() {
        String choice = menu();
        while (!choice.equals("3")) {
            chooseOption(choice);
            choice = menu();
        }
        System.out.println("Exiting...");
    } // end Bank startup

    private void chooseOption(String choice) {
        if (choice.equals("1")) {
            loginAsCustomer();
        } else if (choice.equals("2")) {
            loginAsAdmin();
        } else {
            System.out.println("Invalid choice.");
        }
    } // end chooseOption

    private void loginAsCustomer() { // customers use userName and PIN, catch failed logins
        System.out.print("Username: ");
        String name = CheckingAccount.in.nextLine();
        System.out.print("PIN: ");
        String pin = CheckingAccount.in.nextLine();
        for (Customer customer : customers) {
            if (customer.login(name, pin)) {
                customer.start();
                return;
            }
        }
        System.out.println("Login failed.");
    } // end loginAsCustomer

    private void loginAsAdmin() { // admins use userName and PIN, catch failed logins
        System.out.print("Admin username: ");
        String name = CheckingAccount.in.nextLine();
        System.out.print("PIN: ");
        String pin = CheckingAccount.in.nextLine();
        if (admin.login(name, pin)) {
            admin.start(customers);
        } else {
            System.out.println("Administrator login failed.");
        }
    } // end loginAsAdmin

    public void loadCustomers(ArrayList<Customer> customers) { // uses local customers.ser file for data handling, load sample customers if file doesn't exist yet
        if (!new File(FILE_NAME).exists()) { // check if there is a customers file
            loadSampleCustomers();
            System.out.println("No saved customer data found. Loaded sample customers.");
            return;
        }
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE_NAME))) { // for each customer object, read as customer
            for (Object obj : (ArrayList<?>) input.readObject()) {
                customers.add((Customer) obj);
            }
        } catch (Exception e) { // warn user about invalid customer file
            System.out.println("Error reading " + FILE_NAME + ": " + e.getMessage());
        }
    } // end loadCustomers

    public void saveCustomers(ArrayList<Customer> customers) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) { // for each customer object, save as customer
            output.writeObject(customers);
            System.out.println("Customer data saved to " + FILE_NAME);
        } catch (IOException e) { // warn user about invalid save
            System.out.println("Error saving customer data: " + e.getMessage());
        }
    } // end saveCustomers

    public void fullCustomerReport() { // report balances and interest rates for admin info
        System.out.println("\nCustomer Report:");
        for (Customer customer : customers) {
            System.out.println(customer.getReport());
            System.out.println("-------------------------");
        }
    } // end fullCustomerReport

    public void loadSampleCustomers() { // example format for customers, loaded by default
        Customer c1 = new Customer("alice", "1234");
        c1.checking.setBalance(1000);
        c1.savings.setBalance(5000);
        c1.savings.setInterestRate(0.03);

        Customer c2 = new Customer("bob", "5678");
        c2.checking.setBalance(2000);
        c2.savings.setBalance(3000);
        c2.savings.setInterestRate(0.025);

        customers.add(c1);
        customers.add(c2);
    } // end loadSampleCustomers
} // end Bank class

// ===================================================
// ===================================================
// ADMIN CLASS
// ===================================================
// ===================================================
// Handles Admin menu, serializable to keep admins consistent
// Admins can set interest rates, apply interest, add/remove customers 
class Admin extends User implements Serializable {

    private ArrayList<Customer> customers = new ArrayList<>();

    Admin() {
        this.userName = "admin";
        setPIN("admin");
    } // end Admin constructor (for testing purposes, admin login is just admin)

    String getReport() {
        return "Administrator: " + getUserName();
    } // end getReport

     public String menu() {
        System.out.println("\nAdministrator Menu");
        System.out.println("1. View customer list");
        System.out.println("2. Apply interest to all savings accounts");
        System.out.println("3. Add customer");
        System.out.println("4. Remove customer");
        System.out.println("5. Edit customer interest rate");
        System.out.println("6. Exit");
        System.out.print("Choice: ");
        return CheckingAccount.in.nextLine();
    } // end Admin Menu

    public void start() {
        start(customers);
    } // end startup

    public void start(ArrayList<Customer> customers) {
        this.customers = customers;
        String choice = menu();
        while (!choice.equals("6")) {
            if (choice.equals("1")) {
                viewCustomers();
            } else if (choice.equals("2")) {
                applyInterest();
            } else if (choice.equals("3")) {
                addCustomer();
            } else if (choice.equals("4")) {
                removeCustomer();
            } else if (choice.equals("5")) {
                editInterestRate();
            } else {
                System.out.println("Invalid choice.");
            }
            choice = menu();
        }
        System.out.println("Exiting admin menu...");
    } // end Admin startup

    private void viewCustomers() { // this sends a cohesive list of existing customers
        System.out.println("Getting customer list...\n");
        for (Customer customer : customers) {
            System.out.println(customer.getReport());
            System.out.println("-------------------------");
        }
    } // end viewCustomers

    private void applyInterest() { // set interest rates for customers
        for (Customer customer : customers) {
            customer.savings.calcInterest();
        }
        System.out.println("Interest applied to " + customers.size() + " savings account(s).");
    } // end applyInterest

    private void addCustomer() {
        System.out.print("New customer username: ");
        String name = CheckingAccount.in.nextLine();
        if (name.isEmpty()) { // don't allow empty usernames
            System.out.println("Username cannot be empty. Customer not added.");
            return;
        } // end empty check
        for (Customer customer : customers) { // don't allow duplicate customer names
            if (customer.getUserName().equalsIgnoreCase(name)) {
                System.out.println("That username is already taken. Customer not added.");
                return;
            }
        } // end dupe check

        System.out.print("New customer PIN: ");
        String pin = CheckingAccount.in.nextLine();
        if (pin.isEmpty()) { // don't allow empty pins
            System.out.println("PIN cannot be empty. Customer not added.");
            return;
        } // end empty pin check

        customers.add(new Customer(name, pin));
        System.out.println("Customer '" + name + "' added.");
    } // end addCustomer

    private void removeCustomer() { // option to directly remove customer data
        System.out.print("Username of customer to remove: ");
        String name = CheckingAccount.in.nextLine();
        for (Customer customer : customers) {
            if (customer.getUserName().equalsIgnoreCase(name)) { // confirmation before deletion
                System.out.print("Remove " + customer.getUserName() + " and their accounts? (y/n): ");
                if (CheckingAccount.in.nextLine().equalsIgnoreCase("y")) { // confirmed delete
                    customers.remove(customer);
                    System.out.println("Customer '" + name + "' removed.");
                } else { // for safety, don't want accidental deletions
                    System.out.println("Removal cancelled.");
                }
                return;
            } // end confirmations
        } // end for
        System.out.println("No customer found with that username.");
    } // end removeCustomer

    private void editInterestRate() { // manually edit interest rates for each customer
        System.out.print("Username of customer: ");
        String name = CheckingAccount.in.nextLine().trim();
        for (Customer customer : customers) {
            if (customer.getUserName().equalsIgnoreCase(name)) { // confirmation for new interest rate
                System.out.print("New interest rate (decimal, e.g. 0.03 for 3%): ");
                String input = CheckingAccount.in.nextLine().trim();
                if (input.isEmpty()) { // don't allow empty interest rates
                    System.out.println("Interest rate cannot be empty. Rate not changed.");
                    return;
                } // end if

                double rate;
                try { // only accept interest rate if it is a double
                    rate = Double.parseDouble(input);
                } catch (NumberFormatException e) { // catch invalid values
                    System.out.println("Invalid number. Rate not changed.");
                    return;
                } 
                if (rate < 0) { // don't accept negative interest rates
                    System.out.println("Rate must be a number of 0 or more. Rate not changed.");
                    return;
                } // end if

                System.out.print("Confirm new interest rate: '" + rate + "'? (y/n): ");
                if (CheckingAccount.in.nextLine().equalsIgnoreCase("y")) { // if admin says yes, update interest rate, if no then cancel
                    customer.savings.setInterestRate(rate);
                    System.out.println("Interest rate for " + customer.getUserName()
                            + " set to " + String.format("%.2f%%", rate * 100) + ".");
                } else {
                    System.out.println("Change cancelled.");
                }
                return;
            } // end interest confirmation screen
        } // end for
        System.out.println("No customer found with that username.");
    } // end editInterestRate
} // end Admin class