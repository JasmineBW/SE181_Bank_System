package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MasterControl {
    Bank bank;
    CreateCommandValidator createCommandValidator;
    DepositCommandValidator depositCommandValidator;
    CommandProcessor commandProcessor;
    OutputStorage outputStorage;

    public MasterControl(Bank bank, CreateCommandValidator createCommandValidator, DepositCommandValidator depositCommandValidator,
                         CommandProcessor commandProcessor, OutputStorage outputStorage) {
        this.bank = bank;
        this.createCommandValidator = createCommandValidator;
        this.depositCommandValidator = depositCommandValidator;
        this.commandProcessor = commandProcessor;
        this.outputStorage = outputStorage;

    }

    public ArrayList start(List<String> input) {
        for (String command : input) {

            String split[] = command.split(" ", 0);

            if (Validator.commandChecker(split[0])) {

                if (Objects.equals(split[0], "create")) {
                    if (split.length == 5 && createCommandValidator.validate(split[0], split[1], split[2], split[3], split[4], "")) {
                        commandProcessor.process(split[0], split[1], split[2], split[3], split[4]);
                    } else if (split.length == 4 && createCommandValidator.validate(split[0], split[1], split[2], split[3], "", "")) {
                        commandProcessor.process(split[0], split[1], split[2], split[3]);
                    } else {
                        outputStorage.updateInvalidCommandsList(command);
                    }

                } else if (Objects.equals(split[0], "deposit") && depositCommandValidator.validate(split[0], split[1], split[2], "")) {
                    commandProcessor.process(split[0], split[1], split[2]);
                } else {
                    outputStorage.updateInvalidCommandsList(command);
                }
            } else {
                outputStorage.updateInvalidCommandsList(command);
            }
        }

        return outputStorage.accessInvalidOutputList();
    }
}
