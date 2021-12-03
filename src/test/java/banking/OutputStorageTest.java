package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class OutputStorageTest extends Validator {


    Bank bank = new Bank();
    CreateCommandValidator createCommandValidator = new CreateCommandValidator(bank);
    DepositCommandValidator depositCommandValidator = new DepositCommandValidator(bank);
    OutputStorage outputStorage = new OutputStorage(bank);
    private String command;
    private String accountType;
    private String id;
    private String apr;
    private String amount;
    private String extra;
    private String userInput;

    @Test
    public void store_invalid_create_command_from_invalid_account_type() {
        command = "create";
        accountType = "cdm";
        id = "12345678";
        apr = "0.2";
        amount = "";
        extra = "";
        userInput = String.join(" ", command, accountType, id, apr, amount, extra);
        boolean output = createCommandValidator.validate(command, accountType, id, apr, amount, extra);
        if (!output) {
            outputStorage.updateInvalidCommandsList(userInput);
        }
        assertTrue(outputStorage.accessInvalidOutputList().contains(userInput));

    }

    @Test
    public void store_invalid_deposit_command() {
        command = "deposit";
        id = "200";
        amount = "";
        extra = "";
        userInput = String.join(" ", command, id, amount, extra);
        boolean output = depositCommandValidator.validate(command, id, amount, extra);
        if (!output) {
            outputStorage.updateInvalidCommandsList(userInput);
        }
        assertTrue(outputStorage.accessInvalidOutputList().contains(userInput));

    }

    @Test
    void store_two_invalid_inputs() {
        command = "create";
        accountType = "cdm";
        id = "12345678";
        apr = "0.2";
        amount = "";
        extra = "";
        userInput = String.join(" ", command, accountType, id, apr, amount, extra);
        boolean output = createCommandValidator.validate(command, accountType, id, apr, amount, extra);

        command = "deposit";
        id = "55555555";
        amount = "500";
        extra = "";
        String userInput1 = String.join(" ", command, id, amount);
        boolean output1 = depositCommandValidator.validate(command, id, amount, extra);

        if (!output) {
            outputStorage.updateInvalidCommandsList(userInput);
        }

        if (!output1) {
            outputStorage.updateInvalidCommandsList(userInput1);
        }
        assertTrue(outputStorage.accessInvalidOutputList().contains(userInput));
        assertTrue(outputStorage.accessInvalidOutputList().contains(userInput1));


    }
}
