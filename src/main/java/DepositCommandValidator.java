public class DepositCommandValidator extends Validator {
    int integerId;
    private String accountType;

    public DepositCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command, String id, String amount, String extraArguments) {
        if (!commandChecker(command) || !idChecker(id) || !hasAccountWithIdCheck(id) || extraArguments != "") {
            return false;
        } else {
            integerId = Integer.valueOf(id).intValue();
            if (!bank.containsIdNumber(integerId)) {
                return false;
            } else {
                accountType = bank.getAccountType(integerId);
                if (accountType == "savings") {
                    SavingsCommandValidator savingsDepositCommandValidator = new SavingsCommandValidator(bank);
                    return savingsDepositCommandValidator.depositValidate(amount);
                } else if (accountType == "checking") {
                    CheckingCommandValidator checkingDepositCommandValidator = new CheckingCommandValidator(bank);
                    return checkingDepositCommandValidator.depositValidate(amount);
                } else if (accountType == "cd") {
                    return false;
                }
            }
        }
        return false;
    }
}
