package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    protected Clock clock = new Clock();
    private Account newAccount, retrievedAccount, destinationAccount;
    private double transferFunds;
    private Map<Integer, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public Map<Integer, Account> getListOfAccounts() {
        return accounts;
    }

    public void create(String type, int ID, double APR) {
        if (type.equals("savings")) {
            newAccount = new SavingsAccount(type, ID, APR);
            accounts.put(ID, newAccount);
        } else if (type.equals("checking")) {
            newAccount = new CheckingAccount(type, ID, APR);
            accounts.put(ID, newAccount);
        }
    }

    public void create(String type, int ID, double APR, double amount) {
        if (type.equals("cd")) {
            newAccount = new CDAccount(type, ID, APR, amount);
            accounts.put(ID, newAccount);
        }
    }

    public void deposit(int ID, double amount) {
        retrievedAccount = accounts.get(ID);
        retrievedAccount.deposit(amount);
    }

    public void withdraw(int ID, double amount) {
        retrievedAccount = accounts.get(ID);
        retrievedAccount.withdraw(amount);
    }

    public void transfer(int IDFrom, int IDTo, double amount) {
        retrievedAccount = accounts.get(IDFrom);
        transferFunds = retrievedAccount.withdraw(amount);
        destinationAccount = accounts.get(IDTo);
        destinationAccount.deposit(transferFunds);
    }

    public boolean containsIdNumber(int ID) {
        if (accounts.containsKey(ID)) {
            return true;
        } else {
            return false;
        }
    }

    public String getAccountType(int ID) {
        retrievedAccount = accounts.get(ID);
        return retrievedAccount.getAccountType();
    }

    public Account getAccount(int ID) {
        return accounts.get(ID);
    }

    public void pass(int months) {
        Map<Integer, Account> bank = getListOfAccounts();
        clock.pass(months, bank);

    }
}
