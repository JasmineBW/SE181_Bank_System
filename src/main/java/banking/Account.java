package banking;

public abstract class Account {
    protected double accountBalance;
    protected int longevity;
    protected int availableWithdrawals;
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
    }

    public void deposit(double amount) {
        this.accountBalance += amount;
    }

    public void withdraw(double amount) {
        if (accountBalance < amount) {
            this.accountBalance -= this.accountBalance;
        } else {
            this.accountBalance -= amount;
        }

        if (this.type == "savings") {
            availableWithdrawals--;
        }
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

    public void updateAvailableWithdrawals() {

    }
}



