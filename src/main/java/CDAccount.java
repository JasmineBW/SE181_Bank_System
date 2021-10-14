public class CDAccount extends Account {
    public CDAccount(String type, int ID, double APR, int amount) {
        super(type, ID, APR);
        this.accountBalance = amount;
    }
}
