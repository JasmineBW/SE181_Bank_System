package banking;

import java.util.Objects;

public class CommandProcessor {
    private static Bank bank;
    private int ID, months, IDFrom, IDTo;
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
        } else if (Objects.equals(command, "withdraw")) {
            bank.withdraw(ID, amount);
        }
    }

    public void transferProcess(String command, String idFrom, String idTo, String Amount) {
        IDFrom = Integer.valueOf(idFrom);
        IDTo = Integer.valueOf(idTo);
        amount = Double.valueOf(Amount);
        if (Objects.equals(command, "transfer")) {
            bank.transfer(IDFrom, IDTo, amount);
        }
    }

    public void process(String command, String Months) {
        months = Integer.valueOf(Months);
        if (Objects.equals(command, "pass")) {
            bank.pass(months);
        }
    }
}
