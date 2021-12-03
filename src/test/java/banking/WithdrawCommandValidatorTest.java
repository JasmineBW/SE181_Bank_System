package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawCommandValidatorTest {
    Bank bank;
    WithdrawCommandValidator withdrawCommandValidator;
    private String command = "withdraw";
    private String id;
    private String idSavings = "12345678";
    private String idChecking = "23478902";
    private String amount;
    private String extraArguments = "";
    private boolean output;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        withdrawCommandValidator = new WithdrawCommandValidator(bank);
        bank.create("savings", 12345678, 0.2);
        bank.create("checking", 23478902, 1.5);
        bank.create("cd", 00000001, 5.5, 400);
    }

    @Test
    void valid_savings_withdraw_command_is_entered() {
        amount = "200";
        output = withdrawCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void valid_checking_withdraw_command_is_entered() {
        amount = "20";
        output = withdrawCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void withdraw_amount_is_decimal() {
        amount = "2.56";
        output = withdrawCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void typo_in_withdraw_command() {
        command = "witdraw";
        amount = "50";
        output = withdrawCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void withdraw_from_an_account_that_isnt_in_bank() {
        id = "01010101";
        amount = "50";
        output = withdrawCommandValidator.validate(command, id, amount, extraArguments);
        assertFalse(bank.containsIdNumber(01010101));
        assertFalse(output);
    }

    @Test
    void withdraw_more_than_$1000_from_savings_account() {
        amount = "1000.99";
        output = withdrawCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void withdraw_$1000_from_savings_account() {
        amount = "1000";
        output = withdrawCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void withdraw_less_than_1000_from_savings_account() {
        amount = "900";
        output = withdrawCommandValidator.validate(command, idSavings, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void withdraw_more_than_$400_from_checking_account() {
        amount = "1000";
        output = withdrawCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void withdraw_$400_from_checking_account() {
        amount = "400";
        output = withdrawCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void withdraw_less_than_$400_from_checking_account() {
        amount = "267";
        output = withdrawCommandValidator.validate(command, idChecking, amount, extraArguments);
        assertTrue(output);
    }
}
