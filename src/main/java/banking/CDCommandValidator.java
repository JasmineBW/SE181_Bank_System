package banking;

public class CDCommandValidator extends CreateCommandValidator {

    public CDCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean createValidate(String id, String apr, String amount) {
        if (idChecker(id) && aprChecker(apr) && amountChecker(amount)) {
            double double_amount = Double.valueOf(amount).doubleValue();
            if (double_amount < 1000 || double_amount > 10000) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
