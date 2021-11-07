import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandProcessorTest {
    private String userInput;
    private Bank bank;
    private String command;
    private String accountType;
    private String id;
    private String apr;
    private String amount;
    private CommandProcessor commandProcessor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    public void valid_create_checking_or_savings_command() {
        userInput = "create checking 12345678 1.0";
        command = "create";
        accountType = "checking";
        id = "12345678";
        apr = "1.0";
        amount = "0";
        commandProcessor.process(command, accountType, id, apr, amount);
        assertTrue(bank.containsIdNumber(12345678));
        assertEquals(1.0, bank.getAccount(12345678).getAPR());
    }

    @Test
    public void valid_create_cd_command() {
        command = "create";
        accountType = "CD";
        id = "00000001";
        apr = "3.4";
        amount = "1400";
        commandProcessor.process(command, accountType, id, apr, amount);
        assertTrue(bank.containsIdNumber(00000001));
        assertEquals(3.4, bank.getAccount(00000001).getAPR());
        assertEquals(1400, bank.getAccount(00000001).getAccountBalance());
    }

    @Test
    public void valid_deposit_command_for_existing_account_with_nonzero_balance() {
        command = "deposit";
        id = "12345678";
        amount = "200";
        bank.create("savings", 12345678, 1.5);
        bank.deposit(12345678, 300);
        commandProcessor.process(command, id, amount);
        assertEquals(500, bank.getAccount(12345678).getAccountBalance());
    }

    @Test
    public void valid_deposit_command_for_newly_created_account() {
        command = "deposit";
        id = "24681012";
        amount = "20";
        bank.create("checking", 24681012, 2);
        commandProcessor.process(command, id, amount);
        assertEquals(20, bank.getAccount(24681012).getAccountBalance());
    }
}
