import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class OutputTest extends Validator {


    Bank bank = new Bank();
    CreateCommandValidator createCommandValidator = new CreateCommandValidator(bank);
    DepositCommandValidator depositCommandValidator = new DepositCommandValidator(bank);
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
        assertFalse(output);
        assertTrue(invalidCommandsOutput.accessInvalidOutputList().contains(userInput));

    }

    @Test
    public void store_invalid_deposit_command() {
        command = "deposit";
        id = "200";
        amount = "";
        extra = "";
        boolean output = depositCommandValidator.validate(command, id, amount, extra);
        userInput = String.join(" ", command, id, amount, extra);
        assertFalse(output);
        assertTrue(invalidCommandsOutput.accessInvalidOutputList().contains(userInput));
        System.out.println(invalidCommandsOutput.accessInvalidOutputList());

    }
}
