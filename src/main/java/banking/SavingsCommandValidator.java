package banking;

public class SavingsCommandValidator extends CreateCommandValidator {
    public SavingsCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean createValidate(String id, String apr) {
        return (aprChecker(apr));
    }

    public boolean depositValidate(String amount) {
        if (amountChecker(amount)) {
            double double_amount = Double.valueOf(amount);
            if (double_amount > 2500) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean withdrawValidate(int id, String amount) {
        double double_amount = Double.valueOf(amount);
        int availableWithdrawals = bank.getAccount(id).availableWithdrawals;
        if (double_amount <= 1000 && availableWithdrawals > 0) {
            return true;
        } else {
            return false;
        }
    }
}
