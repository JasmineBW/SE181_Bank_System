public class CommandProcessor {
    private static Bank bank;
    private int ID;
    private Double APR;
    private Double amount;

    CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command, String accountType, String id, String apr, String Amount) {
        ID = Integer.valueOf(id);
        APR = Double.valueOf(apr);
        amount = Double.valueOf(Amount);
        accountType = accountType.toLowerCase();

        if (command == "create") {
            if (!accountType.equals("cd")) {
                bank.create(accountType, ID, APR);
            } else {
                bank.create(accountType, ID, APR, amount);
            }
        }
    }

    public void process(String command, String id, String Amount) {
        ID = Integer.valueOf(id);
        amount = Double.valueOf(Amount);
        if (command == "deposit") {
            bank.deposit(ID, amount);
        }
    }
}
