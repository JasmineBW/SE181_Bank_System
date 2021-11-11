package banking;

public class CDAccount extends Account {
    public CDAccount(String type, int ID, double APR, double amount) {
        super(type.toLowerCase(), ID, APR);
        this.accountBalance = amount;
    }
}
