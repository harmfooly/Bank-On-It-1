import java.io.*;
import java.util.*;

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
