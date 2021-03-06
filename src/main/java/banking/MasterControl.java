package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MasterControl {
    Bank bank;
    CreateCommandValidator createCommandValidator;
    DepositCommandValidator depositCommandValidator;
    WithdrawCommandValidator withdrawCommandValidator;
    TransferCommandValidator transferCommandValidator;
    CommandProcessor commandProcessor;
    OutputStorage outputStorage;

    public MasterControl(Bank bank, CreateCommandValidator createCommandValidator, DepositCommandValidator depositCommandValidator,
                         WithdrawCommandValidator withdrawCommandValidator, TransferCommandValidator transferCommandValidator,
                         CommandProcessor commandProcessor, OutputStorage outputStorage) {
        this.bank = bank;
        this.createCommandValidator = createCommandValidator;
        this.depositCommandValidator = depositCommandValidator;
        this.withdrawCommandValidator = withdrawCommandValidator;
        this.transferCommandValidator = transferCommandValidator;
        this.commandProcessor = commandProcessor;
        this.outputStorage = outputStorage;
    }

    public ArrayList start(List<String> input) {
        for (String command : input) {
            InputParser.split(command);

            if (Validator.commandChecker(InputParser.command)) {
                if (Objects.equals(InputParser.command, "create") && createCommandValidator.validate(InputParser.command,
                        InputParser.accountType, InputParser.id, InputParser.apr, InputParser.amount, InputParser.extra)) {

                    if (Objects.equals(InputParser.accountType, "cd")) {
                        commandProcessor.process(InputParser.command, InputParser.accountType, InputParser.id,
                                InputParser.apr, InputParser.amount);

                    } else {
                        commandProcessor.process(InputParser.command, InputParser.accountType, InputParser.id,
                                InputParser.apr);
                    }

                } else if (Objects.equals(InputParser.command, "deposit") && depositCommandValidator.validate(InputParser.command,
                        InputParser.id, InputParser.amount, InputParser.extra)) {
                    bank.getAccount(Integer.valueOf(InputParser.id)).validCommandsStorage.add(command);
                    commandProcessor.process(InputParser.command, InputParser.id, InputParser.amount);

                } else if (Objects.equals(InputParser.command, "withdraw") && withdrawCommandValidator.validate(InputParser.command,
                        InputParser.id, InputParser.amount, InputParser.extra)) {
                    bank.getAccount(Integer.valueOf(InputParser.id)).validCommandsStorage.add(command);
                    commandProcessor.process(InputParser.command, InputParser.id, InputParser.amount);

                } else if (Objects.equals(InputParser.command, "transfer") && transferCommandValidator.validate(InputParser.command,
                        InputParser.idFrom, InputParser.idTo, InputParser.amount, InputParser.extra)) {
                    bank.getAccount(Integer.valueOf(InputParser.idFrom)).validCommandsStorage.add(command);
                    bank.getAccount(Integer.valueOf(InputParser.idTo)).validCommandsStorage.add(command);
                    commandProcessor.transferProcess(InputParser.command, InputParser.idFrom, InputParser.idTo, InputParser.amount);

                } else if (Objects.equals(InputParser.command, "pass") && PassCommandValidator.validate(InputParser.command,
                        InputParser.months, InputParser.extra)) {
                    commandProcessor.process(InputParser.command, InputParser.months);

                } else {
                    outputStorage.updateInvalidCommandsList(command);

                } //closing bracket for general validation

            } else { //for invalid command storage after general command validation
                outputStorage.updateInvalidCommandsList(command);
            }
        }
        return outputStorage.accessOutputsList(bank);
    }
}
