package banking;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Account {
    protected double accountBalance, funds;
    protected int longevity;
    protected int availableWithdrawals;
    protected ArrayList validCommandsStorage;
    String input = "";
    private String type;
    private int ID;
    private double APR;

    public Account(String type, int ID, double APR) {
        this.type = type;
        this.ID = ID;
        this.APR = APR;
        this.accountBalance = 0;
        this.longevity = 0;
        this.availableWithdrawals = 1;
        this.validCommandsStorage = new ArrayList();
    }

    public void deposit(double amount) {
        this.accountBalance += amount;
    }

    public double withdraw(double amount) {
        if (accountBalance < amount) {
            funds = this.accountBalance;
            this.accountBalance -= this.accountBalance;
        } else {
            funds = amount;
            this.accountBalance -= amount;
        }

        if (Objects.equals(type, "savings")) {
            availableWithdrawals--;
        }
        return funds;
    }

    public int getID() {
        return this.ID;
    }

    public String getAccountType() {
        return this.type;
    }

    public double getAccountBalance() {
        return this.accountBalance;
    }

    public void updateAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getAPR() {
        return this.APR;
    }

    public int getLongevity() {
        return longevity;
    }

}



