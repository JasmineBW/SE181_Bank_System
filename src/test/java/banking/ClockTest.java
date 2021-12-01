package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClockTest {
    public Bank bank;
    int months;
    Account savings;
    Account cd;
    private Map<Integer, Account> accounts;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.create("savings", 12345678, 0.2);
        bank.create("cd", 01234567, 1.2, 0);
        savings = bank.getAccount(12345678);
        cd = bank.getAccount(01234567);
        accounts = bank.getListOfAccounts();
    }

    @Test
    void number_of_months_changes_successfully() {
        Clock.pass(5);
        assertEquals(5, Clock.monthsPassed);
    }

    @Test
    void two_valid_pass_commands_successfully_executed() {
        Clock.pass(5);
        Clock.pass(30);
        assertEquals(35, Clock.monthsPassed);
    }


    @Test
    void close_one_account_with_balance_0() {
        assertEquals(0, savings.getAccountBalance());
        assertTrue(cd.getAccountBalance() == 0);
        Clock.accountUpdate(accounts);
        assertTrue(accounts.isEmpty());
    }

}
