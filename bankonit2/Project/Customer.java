import java.io.*;

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
