package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidatorTest {

    String command = "deposit";
    String idSavings = "12345678";
    String idChecking = "23478902";
    String idCD = "00000001";
    String extraArguments = "";
    private String amount;
    private boolean output;
    private Bank bank;
    private DepositCommandValidator depositCommandValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        depositCommandValidator = new DepositCommandValidator(bank);
        bank.create("savings", 12345678, 0.2);
        bank.create("checking", 23478902, 1.5);
        bank.create("cd", 00000001, 5.5, 400);
    }

    @Test
    public void valid_savings_deposit_command_is_entered() {
        amount = "100";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    public void valid_checking_deposit_command_is_entered() {
        amount = "400";
        output = depositCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    public void deposit_command_into_a_cd_account() {
        amount = "0";
        output = depositCommandValidator.validate(command, idCD, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void deposit_amount_is_a_decimal() {
        amount = "550.99";
        output = depositCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    public void typo_in_deposit_command() {
        command = "dposit";
        amount = "1000";
        output = depositCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void command_is_missing_when_entered() {
        command = "01234567";
        idSavings = "200";
        amount = "";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void id_is_missing_when_command_entered() {
        idChecking = "200";
        amount = "";
        output = depositCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void amount_is_missing_when_command_entered() {
        amount = "";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void too_many_arguments_entered() {
        amount = "0.5";
        extraArguments = "200";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void deposit_into_empty_bank() {
        bank.getListOfAccounts().clear();
        amount = "100";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void deposit_0_in_an_account() {
        amount = "0";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    public void deposit_more_than_2500_in_savings_account() {
        amount = "2500.1";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void deposit_2500_in_savings_account() {
        amount = "2500";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    public void deposit_less_than_2500_in_savings_account() {
        amount = "2499.9";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    public void deposit_negative_amount() {
        amount = "-40";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void deposit_amount_not_all_digits() {
        amount = "fifty bucks";
        output = depositCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    public void deposit_command_is_case_insensitive() {
        command = "DEPOSIT";
        amount = "0";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    public void amount_entered_is_invalid() {
        amount = "-450";
        output = depositCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertFalse(output);
    }
}
