package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> input;
    OutputStorage outputStorage;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        outputStorage = new OutputStorage(bank);
        masterControl = new MasterControl(bank, new CreateCommandValidator(bank), new DepositCommandValidator(bank),
                new WithdrawCommandValidator(bank), new TransferCommandValidator(bank), new CommandProcessor(bank), outputStorage);
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, outputStorage.accessInvalidOutputList().size());
        assertEquals(command, outputStorage.accessInvalidOutputList().get(0));
    }

    @Test
    void typo_in_create_command_is_invalid() {

        input.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.0", outputStorage.accessInvalidOutputList());
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("depositt 12345678 100", outputStorage.accessInvalidOutputList());
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(2, outputStorage.accessInvalidOutputList().size());
        assertEquals("creat checking 12345678 1.0", outputStorage.accessInvalidOutputList().get(0));
        assertEquals("depositt 12345678 100", outputStorage.accessInvalidOutputList().get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("create checking 12345678 1.0", outputStorage.accessInvalidOutputList());
    }

    @Test
    void invalid_to_deposit_in_account_that_does_not_exist() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 00000001 200");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 00000001 200", outputStorage.accessInvalidOutputList());
    }

    @Test
    void invalid_to_deposit_in_cd_account() {
        input.add("create cd 24681012 2 1200");
        input.add("deposit 24681012 500");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 24681012 500", outputStorage.accessInvalidOutputList());
    }

    @Test
    void typo_in_withdraw_command() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 12345678 200");
        input.add("wittdraw 12345678 20");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("wittdraw 12345678 20", outputStorage.accessInvalidOutputList());
    }

    @Test
    void invalid_to_withdraw_more_than_once_from_savings_account_in_a_month() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 12345678 200");
        input.add("withdraw 12345678 20");
        input.add("withdraw 12345678 50");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("withdraw 12345678 50", outputStorage.accessInvalidOutputList());
    }

    @Test
    void invalid_to_withdraw_from_account_that_doesnt_exist() {
        input.add("withdraw 12345678 20");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("withdraw 12345678 20", outputStorage.accessInvalidOutputList());
    }

    @Test
    void typo_in_transfer_command() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 12345678 500");
        input.add("create checking 24681012 1.5");
        input.add("tranfer 12345678 24681012 67");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("tranfer 12345678 24681012 67", outputStorage.accessInvalidOutputList());
    }


    @Test
    void invalid_for_account_to_transfer_to_itself() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 12345678 500");
        input.add("create checking 24681012 1.5");
        input.add("transfer 12345678 12345678 200");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("transfer 12345678 12345678 200", outputStorage.accessInvalidOutputList());
    }

    @Test
    void invalid_to_transfer_from_savings_twice_in_a_month() {
        input.add("create savings 12345678 1.0");
        input.add("create checking 24681012 1.5");
        input.add("deposit 12345678 500");
        input.add("transfer 12345678 24681012 20");
        input.add("transfer 12345678 24681012 50");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("transfer 12345678 24681012 50", outputStorage.accessInvalidOutputList());
    }

    @Test
    void valid_to_move_maximum_amount_from_checking_account_multiple_times_consecutively() {
        input.add("create checking 24681012 1.5");
        input.add("create checking 10000001 1.5");
        input.add("deposit 24681012 1000");
        input.add("transfer 24681012 10000001 400");
        input.add("transfer 24681012 10000001 400");

        List<String> actual = masterControl.start(input);

        assertEquals(0, outputStorage.accessInvalidOutputList().size());
    }

    @Test
    void cd_cannot_be_transferred_from_or_to() {
        input.add("create cd 24681012 2 1200");
        input.add("create checking 10000001 1.5");
        input.add("deposit 10000001 300");
        input.add("transfer 24681012 10000001 500");
        input.add("transfer 10000001 24681012 100");

        List<String> actual = masterControl.start(input);
        System.out.println(actual);


        assertEquals(2, outputStorage.accessInvalidOutputList().size());
        assertEquals("transfer 24681012 10000001 500", outputStorage.accessInvalidOutputList().get(0));
        assertEquals("transfer 10000001 24681012 100", outputStorage.accessInvalidOutputList().get(1));
    }

    @Test
    void invalid_to_pass_0_months() {
        input.add("create savings 12345678 1.0");
        input.add("pass 0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("pass 0", outputStorage.accessInvalidOutputList());
    }


    @Test
    void typo_in_pass_command() {
        input.add("passs 20");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("passs 20", outputStorage.accessInvalidOutputList());
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
        System.out.println(actual);
    }
}
