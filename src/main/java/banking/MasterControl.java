package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MasterControl {
    Bank bank;
    CreateCommandValidator createCommandValidator;
    DepositCommandValidator depositCommandValidator;
    WithdrawCommandValidator withdrawCommandValidator;
    CommandProcessor commandProcessor;
    OutputStorage outputStorage;

    public MasterControl(Bank bank, CreateCommandValidator createCommandValidator, DepositCommandValidator depositCommandValidator,
                         WithdrawCommandValidator withdrawCommandValidator, CommandProcessor commandProcessor, OutputStorage outputStorage) {
        this.bank = bank;
        this.createCommandValidator = createCommandValidator;
        this.depositCommandValidator = depositCommandValidator;
        this.withdrawCommandValidator = withdrawCommandValidator;
        this.commandProcessor = commandProcessor;
        this.outputStorage = outputStorage;
    }

    public ArrayList start(List<String> input) {
        for (String command : input) {
            String[] components = InputParser.split(command);

            if (Validator.commandChecker(InputParser.getCommand())) {

                if (Objects.equals(InputParser.getCommand(), "create") && createCommandValidator.validate(InputParser.command,
                        InputParser.accountType, InputParser.id, InputParser.apr, InputParser.amount, InputParser.extra)) {

                    if (Objects.equals(InputParser.accountType, "cd")) {
                        commandProcessor.process(InputParser.command, InputParser.accountType, InputParser.id,
                                InputParser.apr, InputParser.amount);

                    } else {
                        commandProcessor.process(InputParser.command, InputParser.accountType, InputParser.id,
                                InputParser.apr);
                    }

                } else if (Objects.equals(InputParser.getCommand(), "deposit") && depositCommandValidator.validate(InputParser.command,
                        InputParser.id, InputParser.amount, InputParser.extra)) {
                    commandProcessor.process(InputParser.command, InputParser.id, InputParser.amount);

                } else if (Objects.equals(InputParser.getCommand(), "withdraw") && withdrawCommandValidator.validate(InputParser.command,
                        InputParser.id, InputParser.amount, InputParser.extra)) {
                    commandProcessor.process(InputParser.command, InputParser.id, InputParser.amount);

                } else if (Objects.equals(InputParser.getCommand(), "pass") && PassCommandValidator.validate(InputParser.command,
                        InputParser.months, InputParser.extra)) {
                    commandProcessor.process(InputParser.command, InputParser.months);

                } else {
                    outputStorage.updateInvalidCommandsList(command);
                }

            } //closing bracket for general validation

            else { //for invalid command storage after general command validation
                outputStorage.updateInvalidCommandsList(command);
            }
        }
        return outputStorage.accessInvalidOutputList();
    }
}
