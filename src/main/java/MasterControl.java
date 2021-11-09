import java.util.List;

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

    public List<String> start(List<String> input) {
        for (String command : input) {
            String split[] = command.split(" ", 0);

            //if (Objects.equals(split[0], "create")) {
            if (createCommandValidator.validate(split[0], split[1], split[2], split[3], "", "")) {
                commandProcessor.process(split[0], split[1], split[2], split[3]);
            }
            // } else if (split[0] == "deposit") {
            //   if (depositCommandValidator.validate(split[0], split[1], split[2], "")) {
            //     commandProcessor.process(split[0], split[1], split[2]);
            //}

            else {
                outputStorage.updateInvalidCommandsList(command);
            }
        }
        return outputStorage.accessInvalidOutputList();
    }
}
