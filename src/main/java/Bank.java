import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Integer, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public Map<Integer, Account> getListOfAccounts() {
        return accounts;
    }

    public void create(String type, int ID, double APR) {
        if (type.equals("savings")) {
            Account new_account = new SavingsAccount(type, ID, APR);
            accounts.put(ID, new_account);
        } else if (type.equals("checking")) {
            Account new_account = new CheckingAccount(type, ID, APR);
            accounts.put(ID, new_account);
        }
    }

    public void create(String type, int ID, double APR, double amount) {
        if (type.equals("cd")) {
            Account new_account = new CDAccount(type, ID, APR, amount);
            accounts.put(ID, new_account);
        }
    }

    public void deposit(int ID, double amount) {
        Account retrieved_account = accounts.get(ID);
        retrieved_account.deposit(amount);
    }

    public void withdraw(int ID, double amount) {
        Account retrieved_account = accounts.get(ID);
        retrieved_account.withdraw(amount);
    }

    public boolean containsIdNumber(int ID) {
        if (accounts.containsKey(ID)) {
            return true;
        } else {
            return false;
        }
    }

    public String getAccountType(int ID) {
        Account retrieved_account = accounts.get(ID);
        return retrieved_account.getAccountType();
    }

    public Account getAccount(int ID) {
        return accounts.get(ID);
    }
}
