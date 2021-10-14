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
        if (type == "savings") {
            Account new_account = new SavingsAccount(type, ID, APR);
            accounts.put(ID, new_account);
        } else if (type == "checking") {
            Account new_account = new CheckingAccount(type, ID, APR);
            accounts.put(ID, new_account);
        }
    }

    public void create(String type, int ID, double APR, int amount) {
        if (type == "cd") {
            Account new_account = new CDAccount(type, ID, APR, amount);
            accounts.put(ID, new_account);
        }
    }

    public void deposit(int ID, int amount) {
        Account retrieved_account = accounts.get(ID);
        retrieved_account.deposit(amount);
    }

    public void withdraw(int ID, double amount) {
        Account retrieved_account = accounts.get(ID);
        retrieved_account.withdraw(amount);
    }
}
