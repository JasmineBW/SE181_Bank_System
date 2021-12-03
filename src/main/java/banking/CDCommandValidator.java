package banking;

import java.util.Objects;

public class CDCommandValidator extends CreateCommandValidator {

    public CDCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean createValidate(String id, String apr, String amount) {
        if (idChecker(id) && aprChecker(apr) && amountChecker(amount)) {
            double double_amount = Double.valueOf(amount);
            if (double_amount < 1000 || double_amount > 10000) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean withdrawValidate(int id, String amount) {
        if (amountChecker(amount)) {
            double double_amount = Double.valueOf(amount);
            if (bank.getAccount(id).getLongevity() >= 12 && Objects.equals(double_amount, bank.getAccount(id).getAccountBalance())) {
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
