package banking;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Clock {

    public static int monthsPassed = 0;
    static int minimum_balance_fee = 25;
    static Account account;
    static Set<Map.Entry<Integer, Account>> accountEntries;
    static Iterator<Map.Entry<Integer, Account>> iterator;
    private static double initial_account_balance, new_account_balance;

    public static void pass(int months, Map<Integer, Account> bank) {
        monthsPassed += months;
        accountUpdate(bank);
    }

    public static void accountUpdate(Map<Integer, Account> bank) {
        accountEntries = bank.entrySet();
        iterator = accountEntries.iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, Account> accountEntry = iterator.next();
            account = accountEntry.getValue();
            initial_account_balance = account.getAccountBalance();

            if (initial_account_balance == 0) {
                iterator.remove();
            } else if (initial_account_balance < 100) {
                new_account_balance = initial_account_balance - minimum_balance_fee;
                account.updateAccountBalance(new_account_balance);
                //calculate apr
            } else if (initial_account_balance >= 100) {
                //calculate apr
            }

        }
    }
}
