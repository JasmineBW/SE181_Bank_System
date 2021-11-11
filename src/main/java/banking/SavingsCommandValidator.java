package banking;

public class SavingsCommandValidator extends CreateCommandValidator {

    public SavingsCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean createValidate(String id, String apr) {
        if (idChecker(id) && aprChecker(apr)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean depositValidate(String amount) {
        if (amountChecker(amount)) {
            double double_amount = Double.valueOf(amount).doubleValue();
            if (double_amount > 2500) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
