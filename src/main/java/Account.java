public class Account implements BankCommands {
    protected int accountBalance;
    private String type;
    private int ID;
    private double APR;

    public Account(String type, int ID, double APR) {
        this.type = type;
        this.ID = ID;
        this.APR = APR;
        this.accountBalance = 0;
    }

    public static Account create(String type, int ID, double APR) {
        return new Account(type, ID, APR);
    }

    @Override
    public void deposit(int amount) {
        this.accountBalance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (accountBalance < amount) {
            this.accountBalance -= this.accountBalance;
        } else {
            this.accountBalance -= amount;
        }
    }

    public int getID() {
        return this.ID;
    }

    public String getAccountType() {
        return this.type;
    }

    public int getAccountBalance() {
        return this.accountBalance;
    }


}



