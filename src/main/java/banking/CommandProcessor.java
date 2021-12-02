package banking;

import java.util.Objects;

public class CommandProcessor {
    private static Bank bank;
    private int ID, months;
    private Double APR;
    private Double amount;

    CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command, String accountType, String id, String apr) {
        ID = Integer.valueOf(id);
        APR = Double.valueOf(apr);
        accountType = accountType.toLowerCase();

        if (Objects.equals(command, "create")) {
            if (Objects.equals(accountType, "savings") || Objects.equals(accountType, "checking")) {
                bank.create(accountType, ID, APR);
            }
        }
    }

    public void process(String command, String accountType, String id, String apr, String Amount) {
        ID = Integer.valueOf(id);
        APR = Double.valueOf(apr);
        amount = Double.valueOf(Amount);
        accountType = accountType.toLowerCase();

        if (Objects.equals(command, "create")) {
            if (Objects.equals(accountType, "cd")) {
                bank.create(accountType, ID, APR, amount);
            }
        }
    }

    public void process(String command, String id, String Amount) {
        ID = Integer.valueOf(id);
        amount = Double.valueOf(Amount);
        if (Objects.equals(command, "deposit")) {
            bank.deposit(ID, amount);
        }
    }

    public void process(String command, String Months) {
        months = Integer.valueOf(Months);
        if (Objects.equals(command, "pass")) {
            bank.pass(months);
        }
    }
}
