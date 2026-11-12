import java.io.*;
import java.util.*;

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
