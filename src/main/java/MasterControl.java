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
            if (createCommandValidator.validate(split[0], split[1], split[2], split[3], "", "")) {
                commandProcessor.process(split[0], split[1], split[2], split[3]);
            } else {
                outputStorage.updateInvalidCommandsList(command);
                System.out.println(outputStorage.accessInvalidOutputList());
            }
        }
        return outputStorage.accessInvalidOutputList();
    }
}
