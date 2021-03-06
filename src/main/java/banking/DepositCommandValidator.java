package banking;

import java.util.Objects;

public class DepositCommandValidator extends Validator {
    int integerId;

    public DepositCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command, String id, String amount, String extraArguments) {
        if (!commandChecker(command) || !idChecker(id) || !hasAccountWithIdCheck(id) || !Objects.equals(extraArguments, "")) {
            return false;

        } else {
            integerId = Integer.valueOf(id);
            String accountType = bank.getAccountType(integerId);
            if (Objects.equals(accountType, "savings")) {
                SavingsCommandValidator savingsDepositCommandValidator = new SavingsCommandValidator(bank);
                return savingsDepositCommandValidator.depositValidate(amount);

            } else if (Objects.equals(accountType, "checking")) {
                CheckingCommandValidator checkingDepositCommandValidator = new CheckingCommandValidator(bank);
                return checkingDepositCommandValidator.depositValidate(amount);

            } else {
                return false;
            }
        }
    }
}

