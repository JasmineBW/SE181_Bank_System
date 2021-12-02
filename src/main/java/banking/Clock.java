package banking;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Clock {

    public static int monthsPassed = 0;
    static int minimum_balance_fee = 25;
    static Account account;
    static APRCalculator calculator = new APRCalculator();
    static Set<Map.Entry<Integer, Account>> accountEntries;
    static Iterator<Map.Entry<Integer, Account>> iterator;
    private static double initial_account_balance, newBalance;

    public static void pass(int months, Map<Integer, Account> bank) {
        monthsPassed += months;
        int iters;
        for (iters = 0; iters < months; iters++) {
            accountUpdate(bank);
        }
    }

    public static void accountUpdate(Map<Integer, Account> bank) {
        accountEntries = bank.entrySet();
        iterator = accountEntries.iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, Account> accountEntry = iterator.next();
            account = accountEntry.getValue();
            initial_account_balance = account.getAccountBalance();
            if (initial_account_balance == 0.0) {
                iterator.remove();

            } else if (initial_account_balance < 100) {
                if (minimum_balance_fee > initial_account_balance) {
                    newBalance = 0;
                    iterator.remove();
                } else if (initial_account_balance >= minimum_balance_fee) {
                    newBalance = initial_account_balance - minimum_balance_fee;
                }
                account.updateAccountBalance(newBalance);
                if (account.getAccountType() == "cd") {
                    calculator.CDCalculateAPR(account);
                } else {
                    calculator.calculateApr(account);
                }

            } else if (initial_account_balance >= 100) {
                if (account.getAccountType() == "cd") {
                    calculator.CDCalculateAPR(account);
                } else {
                    calculator.calculateApr(account);
                }
            }

        }
    }
}