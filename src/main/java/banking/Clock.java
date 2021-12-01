package banking;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Clock {

    public static int monthsPassed;
    static Account account;
    static int accountId;

    public static void pass(int months) {
        monthsPassed += months;
    }

    public static void accountUpdate(Map<Integer, Account> bank) {
        Set<Map.Entry<Integer, Account>> accountEntries = bank.entrySet();
        Iterator<Map.Entry<Integer, Account>> iterator = accountEntries.iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, Account> accountEntry = iterator.next();
            account = accountEntry.getValue();

            if (account.getAccountBalance() == 0) {
                iterator.remove();
            }
        }
    }
}
