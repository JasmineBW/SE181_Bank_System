package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ClockTest {
    public Bank bank;
    Account savings, cd, checking;
    private Map<Integer, Account> accounts;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.create("savings", 12345678, 0.6);
        bank.create("cd", 01234567, 1.2, 0);
        savings = bank.getAccount(12345678);
        cd = bank.getAccount(01234567);
        accounts = bank.getListOfAccounts();
        Clock.monthsPassed = 0;
    }

    @Test
    void number_of_months_changes_successfully() {
        Clock.monthsPassed = 0;
        savings.deposit(500);
        Clock.pass(5, accounts);
        assertEquals(5, Clock.monthsPassed);
    }

    @Test
    void two_valid_pass_commands_successfully_executed() {
        Clock.monthsPassed = 0;
        savings.deposit(500);
        Clock.pass(5, accounts);
        Clock.pass(30, accounts);
        assertEquals(35, Clock.monthsPassed);
    }

    @Test
    void close_one_account_with_balance_0() {
        savings.deposit(500);
        Clock.pass(1, accounts);
        assertTrue(bank.containsIdNumber(12345678));
        assertFalse(bank.containsIdNumber(01234567));
    }

    @Test
    void close_two_accounts_with_balance_0() {
        assertEquals(0, savings.getAccountBalance());
        assertTrue(cd.getAccountBalance() == 0);
        Clock.pass(1, accounts);
        assertTrue(accounts.isEmpty());
    }

    //from here on mbf = minimum_bank_fee
    @Test
    void mbf_not_charged_when_balance_100() {
        savings.deposit(100);
        Clock.pass(1, accounts);
        assertEquals(100, savings.getAccountBalance());
    }

    @Test
    void mbf_charged_when_account_balance_below_100() {
        savings.deposit(50);
        Clock.pass(1, accounts);
        assertEquals(25, savings.getAccountBalance());
    }

    @Test
    void mbf_charged_on_account_balance_99_99() {
        savings.deposit(99.99);
        Clock.pass(1, accounts);
        assertEquals(74.99, savings.getAccountBalance());
    }

    @Test
    void account_is_charged_mbf_after_one_month() {
        savings.deposit(75);
        Clock.pass(1, accounts);
        assertEquals(50, savings.getAccountBalance());
    }

    @Test
    void one_account_correctly_charged_after_more_than_one_month_passes() {
        savings.deposit(80);
        Clock.pass(3, accounts);
        assertEquals(5, savings.getAccountBalance());
    }

    @Test
    void multiple_accounts_correctly_charged_after_more_than_one_month_passes() {
        savings.deposit(90);
        bank.create("checking", 87654321, 1.2);
        checking = bank.getAccount(87654321);
        checking.deposit(79);
        Clock.pass(2, accounts);
        assertEquals(40, savings.getAccountBalance());
        assertEquals(29, checking.getAccountBalance());
    }

    @Test
    void account_balance_doesnt_go_below_0_after_mbf_charged() {
        savings.deposit(99.99);
        Clock.pass(4, accounts);
        assertEquals(0, savings.getAccountBalance());
    }

    @Test
    void accounts_close_if_mfb_charged_and_balance_gets_to_0() {
        savings.deposit(99.99);
        Clock.pass(4, accounts);
        assertFalse(bank.containsIdNumber(12345678));
    }

    @Test
    void apr_calculated_correctly_for_one_month_passed() {
        savings.deposit(5000);
        Clock.pass(1, accounts);
        assertTrue(savings.getAccountBalance() == 5002.50);
    }

    @Test
    void apr_calculated_correctly_for_multiple_months_passed() {
        savings.deposit(5000);
        Clock.pass(2, accounts);
        assertTrue(savings.getAccountBalance() == 5005.00125);
    }

    @Test
    void apr_calculated_correctly_for_cd_account() {
        bank.create("cd", 00000001, 0.5, 200);
        cd = bank.getAccount(00000001);
        Clock.pass(1, accounts);
        assertEquals(200.3335417245431, cd.getAccountBalance());
    }
}
