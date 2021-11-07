import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
    Bank bank;
    private String account_type;
    private String command;
    private String id;
    private String apr;
    private String amount;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    public void supported_command_entered() {
        command = "pass";
        boolean output = Validator.commandChecker(command);
        assertTrue(output);
    }

    @Test
    public void unsupported_command_entered() {
        command = "delete";
        boolean output = Validator.commandChecker(command);
        assertFalse(output);
    }

    @Test
    public void commands_are_case_insensitive() {
        command = "dEpoSIt";
        boolean output = Validator.commandChecker(command);
        assertTrue(output);
    }

    @Test
    public void valid_account_type_is_entered() {
        account_type = "savings";
        boolean output = Validator.accountTypeChecker(account_type);
        assertTrue(output);
    }

    @Test
    public void invalid_account_type_is_entered() {
        account_type = "premium gold";
        boolean output = Validator.accountTypeChecker(account_type);
        assertFalse(output);
    }

    @Test
    public void account_type_is_case_insensitive() {
        account_type = "saVInGs";
        boolean output = Validator.accountTypeChecker(account_type);
        assertTrue(output);
    }

    @Test
    public void id_of_wrong_length_is_entered() {
        id = "012345";
        boolean output = Validator.idChecker(id);
        assertFalse(output);
    }

    @Test
    public void id_is_not_a_number() {
        id = "123abc45";
        boolean output = Validator.idChecker(id);
        assertFalse(output);
    }

    @Test
    public void apr_is_invalid() {
        apr = "200";
        boolean output = Validator.aprChecker(apr);
        assertFalse(output);
    }

    @Test
    public void valid_apr_entered_is_an_integer() {
        apr = "5";
        boolean output = Validator.aprChecker(apr);
        assertTrue(output);
    }

    @Test
    public void valid_apr_entered_is_a_float() {
        apr = "0.15";
        boolean output = Validator.aprChecker(apr);
        assertTrue(output);
    }

    @Test
    public void amount_is_entered_in_words() {
        amount = "five hundred";
        boolean output = Validator.amountChecker(amount);
        assertFalse(output);
    }

    @Test
    public void negative_amount_is_entered() {
        amount = "-500";
        boolean output = Validator.amountChecker(amount);
        assertFalse(output);
    }

    @Test
    public void large_amount_value_is_entered() {
        amount = "50000000000000452220";
        boolean output = Validator.amountChecker(amount);
        assertTrue(output);
    }

    @Test
    public void amount_is_entered_as_a_float() {
        amount = "0.50";
        boolean output = Validator.amountChecker(amount);
        assertTrue(output);
    }

    @Test
    public void amount_is_not_all_digits() {
        amount = "500 x10^2";
        String amount2 = "100,000";
        boolean output = Validator.amountChecker(amount);
        boolean output2 = Validator.amountChecker(amount2);
        assertEquals(output, output2);
    }

    @Test
    public void typo_in_account_type() {
        account_type = "CED";
        boolean output = Validator.accountTypeChecker(account_type);
        assertFalse(output);
    }
}
