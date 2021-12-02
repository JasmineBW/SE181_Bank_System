package banking;

public class APRCalculator {
    private double APR;
    private double newBalance, accountBalance, interest;

    public void calculateApr(Account account) {
        APR = account.getAPR();
        accountBalance = account.getAccountBalance();
        APR = APR / 100;
        interest = (APR / 12) * accountBalance;
        newBalance = accountBalance + interest;
        account.updateAccountBalance(newBalance);
    }

    public void CDCalculateAPR(Account account) {
        APR = account.getAPR();
        APR = APR / 100;

        int counter = 0;
        while (counter < 4) {
            accountBalance = account.getAccountBalance();
            interest = (APR / 12) * accountBalance;
            newBalance = accountBalance + interest;
            account.updateAccountBalance(newBalance);
            counter++;
        }
    }
}
