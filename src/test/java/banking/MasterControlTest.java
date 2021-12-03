package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(bank, new CreateCommandValidator(bank), new DepositCommandValidator(bank),
                new WithdrawCommandValidator(bank), new CommandProcessor(bank), new OutputStorage());
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void typo_in_create_command_is_invalid() {

        input.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("depositt 12345678 100", actual.get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("create checking 12345678 1.0", actual);
    }

    @Test
    void invalid_to_deposit_in_account_that_does_not_exist() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 00000001 200");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 00000001 200", actual);
    }

    @Test
    void invalid_to_deposit_in_cd_account() {
        input.add("create cd 24681012 2 1200");
        input.add("deposit 24681012 500");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 24681012 500", actual);
    }

    @Test
    void invalid_to_pass_0_months() {
        input.add("create savings 12345678 1.0");
        input.add("pass 0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("pass 0", actual);
    }

    @Test
    void typo_in_pass_command() {
        input.add("passs 20");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("passs 20", actual);
    }
}
