import java.io.*;

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
