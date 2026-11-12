import java.io.*;

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
