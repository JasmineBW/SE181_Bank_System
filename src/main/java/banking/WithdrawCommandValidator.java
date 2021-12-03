package banking;

import java.util.Objects;

public class WithdrawCommandValidator extends Validator {
    int integerId;

    public WithdrawCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command, String id, String amount, String extraArguments) {
        if (!commandChecker(command) || !idChecker(id) || !hasAccountWithIdCheck(id) || !Objects.equals(extraArguments, "")) {
            return false;

        } else {
            integerId = Integer.valueOf(id);
            if (!bank.containsIdNumber(integerId)) {
                return false;
            } else {
                String accountType = bank.getAccountType(integerId);
                if (Objects.equals(accountType, "savings")) {
                    SavingsCommandValidator savingsWithdrawCommandValidator = new SavingsCommandValidator(bank);
                    return savingsWithdrawCommandValidator.withdrawValidate(integerId, amount);

                } else if (Objects.equals(accountType, "checking")) {
                    CheckingCommandValidator checkingWithdrawCommandValidator = new CheckingCommandValidator(bank);
                    return checkingWithdrawCommandValidator.withdrawValidate(amount);

                } else if (Objects.equals(accountType, "cd")) {
                    CDCommandValidator cdWithdrawCommandValidator = new CDCommandValidator(bank);
                    return cdWithdrawCommandValidator.withdrawValidate(integerId, amount);
                }
            }
        }
        return true;
    }
}
