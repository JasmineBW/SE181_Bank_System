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
            if (!bank.containsIdNumber(integerId)) {
                return false;

            } else {
                String accountType = bank.getAccountType(integerId);
                if (Objects.equals(accountType, "savings")) {
                    SavingsCommandValidator savingsDepositCommandValidator = new SavingsCommandValidator(bank);
                    return savingsDepositCommandValidator.depositValidate(amount);

                } else if (Objects.equals(accountType, "checking")) {
                    CheckingCommandValidator checkingDepositCommandValidator = new CheckingCommandValidator(bank);
                    return checkingDepositCommandValidator.depositValidate(amount);

                } else if (Objects.equals(accountType, "cd")) {
                    return false;
                }
            }
        }
        return false;
    }
}
