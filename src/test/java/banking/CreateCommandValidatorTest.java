package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidatorTest {
    String command = "create";
    String accountTypeSavings = "savings";
    String accountTypeChecking = "checking";
    String accountTypeCD = "CD";
    String idSavings = "01234567";
    String idChecking = "23478902";
    String idCD = "00000001";
    String amount = "";
    String extra = "";
    Bank bank;
    CreateCommandValidator createCommandValidator;
    private String id;
    private String apr;
    private boolean output;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createCommandValidator = new CreateCommandValidator(bank);
    }

    @Test
    public void insufficient_parameters_passed_into_create_command() {
        id = "0.2";
        apr = "";
        output = createCommandValidator.validate(command, accountTypeSavings, id, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void too_many_parameters_passed_into_create_command() {
        apr = "2";
        amount = "2000";
        extra = "a";
        output = createCommandValidator.validate(command, accountTypeSavings, idSavings, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void valid_create_savings_command() {
        apr = "0.2";
        output = createCommandValidator.validate(command, accountTypeSavings, idSavings, apr, amount, extra);
        assertTrue(output);
    }

    @Test
    public void valid_create_checking_command() {
        apr = "10";
        output = createCommandValidator.validate(command, accountTypeChecking, idChecking, apr, amount, extra);
        assertTrue(output);
    }

    @Test
    public void valid_create_CD_command() {
        apr = "1.256";
        amount = "1100";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertTrue(output);
    }

    @Test
    public void create_CD_command_with_amount_too_large() {
        apr = "1.256";
        amount = "11000";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void create_CD_command_with_amount_too_small() {
        apr = "1.256";
        amount = "1";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void create_CD_command_with_amount_1000() {
        apr = "1.256";
        amount = "1000";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertTrue(output);
    }

    @Test
    public void create_CD_command_with_amount_999() {
        apr = "1.256";
        amount = "999";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void create_CD_command_with_amount_1001() {
        apr = "1.256";
        amount = "1001";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertTrue(output);
    }

    @Test
    public void create_CD_command_with_amount_10000() {
        apr = "1.256";
        amount = "10000";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertTrue(output);
    }

    @Test
    public void create_CD_command_with_amount_lesser_bound_10000() {
        apr = "1.256";
        amount = "9999.999";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertTrue(output);
    }

    @Test
    public void create_CD_command_with_amount_greater_bound_of_10000() {
        apr = "1.256";
        amount = "10000.1";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void create_an_account_with_an_id_already_in_bank() {
        bank.create("cd", 00000001, 1.43, 1500);
        apr = "0.55432";
        amount = "2000";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void typo_in_create_command() {
        command = "crete";
        output = createCommandValidator.validate(command, accountTypeChecking, idChecking, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void create_command_omitted() {
        command = "";
        apr = "1";
        amount = "1001";
        output = createCommandValidator.validate(command, accountTypeCD, idCD, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void account_type_missing() {
        accountTypeChecking = "";
        apr = "0";
        output = createCommandValidator.validate(command, accountTypeChecking, idChecking, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void apr_parameter_missing() {
        apr = "";
        output = createCommandValidator.validate(command, accountTypeChecking, idChecking, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void invalid_id_entered() {
        idSavings = "abcdefgh";
        apr = "0";
        output = createCommandValidator.validate(command, accountTypeSavings, idSavings, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void invalid_apr_entered() {
        apr = "0,6";
        output = createCommandValidator.validate(command, accountTypeChecking, idChecking, apr, amount, extra);
        assertFalse(output);
    }

    @Test
    public void apr_is_negative() {
        apr = "-5.5";
        output = createCommandValidator.validate(command, accountTypeChecking, idChecking, apr, amount, extra);
        assertFalse(output);
    }
}
