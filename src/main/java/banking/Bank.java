package banking;

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
            Account newAccount = new SavingsAccount(type, ID, APR);
            accounts.put(ID, newAccount);
        } else if (type.equals("checking")) {
            Account newAccount = new CheckingAccount(type, ID, APR);
            accounts.put(ID, newAccount);
        }
    }

    public void create(String type, int ID, double APR, double amount) {
        if (type.equals("cd")) {
            Account newAccount = new CDAccount(type, ID, APR, amount);
            accounts.put(ID, newAccount);
        }
    }

    public void deposit(int ID, double amount) {
        Account retrievedAccount = accounts.get(ID);
        retrievedAccount.deposit(amount);
    }

    public void withdraw(int ID, double amount) {
        Account retrievedAccount = accounts.get(ID);
        retrievedAccount.withdraw(amount);
    }

    public boolean containsIdNumber(int ID) {
        if (accounts.containsKey(ID)) {
            return true;
        } else {
            return false;
        }
    }

    public String getAccountType(int ID) {
        Account retrievedAccount = accounts.get(ID);
        return retrievedAccount.getAccountType();
    }

    public Account getAccount(int ID) {
        return accounts.get(ID);
    }

    public void pass(int months) {
        Clock.pass(months);
        Map<Integer, Account> bank = getListOfAccounts();
        Clock.accountUpdate(bank);
    }
}
