import java.util.Objects;

public class CreateCommandValidator extends Validator {
    private String userInput;

    public CreateCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command, String accountType, String id, String apr, String amount, String extra) {
        if (commandChecker(command) && accountTypeChecker(accountType) && Objects.equals(extra, "") && idChecker(id) &&
                !hasAccountWithIdCheck(id)) {
            accountType = accountType.toLowerCase();

            if (!Objects.equals(amount, "")) {
                if (accountType.equals("savings") || accountType.equals("checking")) {
                    return false;
                }
                if (accountType.equals("cd")) {
                    CDCommandValidator cdCreateCommandValidator = new CDCommandValidator(bank);
                    return cdCreateCommandValidator.createValidate(id, apr, amount);
                }
            } else {
                if (accountType.equals("savings")) {
                    SavingsCommandValidator savingsCreateCommandValidator = new SavingsCommandValidator(bank);
                    return savingsCreateCommandValidator.createValidate(id, apr);

                } else if (accountType.equals("checking")) {
                    CheckingCommandValidator checkingCommandValidator = new CheckingCommandValidator(bank);
                    return checkingCommandValidator.createValidate(id, apr);
                }
            }

        }
        userInput = String.join(" ", command, accountType, id, apr, amount, extra);
        invalidCommandsOutput.updateInvalidCommandsList(userInput);
        return false;
    }
}
