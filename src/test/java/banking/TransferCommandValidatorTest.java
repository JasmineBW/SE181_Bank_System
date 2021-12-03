package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferCommandValidatorTest {

    Bank bank;
    private String command = "transfer";
    private String idFrom, idTo;
    private String savingsId = "12345678", checkingId = "87654321", CdId = "11118888";
    private String amount;
    private String extraArguments = "";
    private Account Savings, Checking, Cd;
    private boolean output;
    private TransferCommandValidator transferCommandValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        transferCommandValidator = new TransferCommandValidator(bank);
        bank.create("savings", 12345678, 0.5);
        bank.create("checking", 87654321, 1.4);
        bank.create("cd", 11118888, 3.5, 1500);
        Savings = bank.getAccount(12345678);
        Checking = bank.getAccount(87654321);
        Cd = bank.getAccount(11118888);
    }

    @Test
    void valid_transfer_command_from_savings_account() {
        idFrom = savingsId;
        idTo = checkingId;
        amount = "30";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void valid_transfer_command_from_checking_account() {
        idFrom = checkingId;
        idTo = savingsId;
        amount = "30";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void transfer_amount_is_decimal() {
        idFrom = savingsId;
        idTo = checkingId;
        amount = "5.5";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void transfer_amount_not_a_number() {
        idFrom = savingsId;
        idTo = checkingId;
        amount = "fifty";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void transfer_from_account_that_doesnt_exist() {
        idFrom = "00000018";
        idTo = checkingId;
        amount = "200";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void transfer_to_account_that_doesnt_exist() {
        idFrom = savingsId;
        idTo = "11111111";
        amount = "1";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void invalid_command_to_transfer_more_than_$400_from_checking_at_once() {
        idFrom = checkingId;
        idTo = savingsId;
        amount = "500";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void invalid_command_to_transfer_more_than_$1000_from_savings() {
        idFrom = savingsId;
        idTo = checkingId;
        amount = "1500";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void command_to_transfer_$1000_into_checking_account() {
        idFrom = savingsId;
        idTo = checkingId;
        amount = "1000";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertTrue(output);
    }

    @Test
    void invalid_command_to_transfer_to_cd_account() {
        idFrom = checkingId;
        idTo = CdId;
        amount = "300";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void invalid_command_to_transfer_from_cd_account() {
        idFrom = CdId;
        idTo = savingsId;
        amount = "1500";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void command_argument_missing() {
        command = savingsId;
        idFrom = checkingId;
        idTo = "500";
        amount = "";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void an_id_argument_missing() {
        idFrom = checkingId;
        idTo = "500";
        amount = "";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void amount_argument_missing() {
        idFrom = savingsId;
        idTo = checkingId;
        amount = "";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void too_many_arguments_passed_into_command() {
        idFrom = checkingId;
        idTo = savingsId;
        amount = "400";
        extraArguments = ".";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertFalse(output);
    }

    @Test
    void transferring_to_accounts_of_same_type() {
        bank.create("savings", 12312312, 2.3);
        bank.deposit(12345678, 1000);
        System.out.println(bank.getListOfAccounts());
        idFrom = savingsId;
        idTo = "12312312";
        amount = "500";
        output = transferCommandValidator.validate(command, idFrom, idTo, amount, extraArguments);
        assertTrue(output);
    }
}
