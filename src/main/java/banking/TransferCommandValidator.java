package banking;

import java.util.Objects;

public class TransferCommandValidator extends Validator {
    boolean withdrawValidity, depositValidity;
    WithdrawCommandValidator withdrawCommandValidator;
    DepositCommandValidator depositCommandValidator;

    public TransferCommandValidator(Bank bank) {
        this.bank = bank;
        withdrawCommandValidator = new WithdrawCommandValidator(bank);
        depositCommandValidator = new DepositCommandValidator(bank);
    }

    public boolean validate(String command, String idFrom, String idTo, String amount, String extra) {
        if (!commandChecker(command) || !idChecker(idFrom) || !idChecker(idTo) || !hasAccountWithIdCheck(idFrom) ||
                !hasAccountWithIdCheck(idTo) || !Objects.equals(extra, "") || Objects.equals(idTo, idFrom)) {
            return false;

        } else {
            int integerIdFrom = Integer.valueOf(idFrom), integerIdTo = Integer.valueOf(idTo);

            if (Objects.equals(bank.getAccountType(integerIdFrom), "cd") ||
                    Objects.equals(bank.getAccountType(integerIdTo), "cd")) {
                return false;
            }

            withdrawValidity = withdrawCommandValidator.validate(command, idFrom, amount, extra);
            depositValidity = depositCommandValidator.validate(command, idTo, amount, extra);

            if (withdrawValidity && depositValidity) {
                return true;
            } else {
                return false;
            }
        }
    }
}
