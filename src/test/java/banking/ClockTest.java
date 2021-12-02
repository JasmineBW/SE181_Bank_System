package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ClockTest {
    public Bank bank;
    Account savings, cd, checking;
    Clock clock;
    private Map<Integer, Account> accounts;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.create("savings", 12345678, 0.6);
        bank.create("cd", 01234567, 1.2, 0);
        savings = bank.getAccount(12345678);
        cd = bank.getAccount(01234567);
        accounts = bank.getListOfAccounts();
        clock = new Clock();
        clock.monthsPassed = 0;
    }

    @Test
    void number_of_months_changes_successfully() {
        clock.monthsPassed = 0;
        savings.deposit(500);
        clock.pass(5, accounts);
        assertEquals(5, clock.monthsPassed);
    }

    @Test
    void two_valid_pass_commands_successfully_executed() {
        clock.monthsPassed = 0;
        savings.deposit(500);
        clock.pass(5, accounts);
        clock.pass(30, accounts);
        assertEquals(35, clock.monthsPassed);
    }

    @Test
    void close_one_account_with_balance_0() {
        savings.deposit(500);
        clock.pass(1, accounts);
        assertTrue(bank.containsIdNumber(12345678));
        assertFalse(bank.containsIdNumber(01234567));
    }

    @Test
    void close_two_accounts_with_balance_0() {
        assertEquals(0, savings.getAccountBalance());
        assertEquals(0, cd.getAccountBalance());
        clock.pass(1, accounts);
        assertTrue(accounts.isEmpty());
    }

    //from here on mbf = minimum_bank_fee
    @Test
    void mbf_not_charged_when_balance_100() {
        savings.deposit(100);
        clock.pass(1, accounts);
        assertEquals(100.05, savings.getAccountBalance());
    }

    @Test
    void mbf_charged_when_account_balance_below_100() {
        savings.deposit(50);
        clock.pass(1, accounts);
        assertEquals(25.0125, savings.getAccountBalance());
    }

    @Test
    void mbf_charged_on_account_balance_99_99() {
        savings.deposit(99.99);
        clock.pass(1, accounts);
        assertEquals(75.027495, savings.getAccountBalance());
    }

    @Test
    void account_is_charged_mbf_after_one_month() {
        savings.deposit(75);
        clock.pass(1, accounts);
        assertEquals(50.025, savings.getAccountBalance());
    }

    @Test
    void one_account_correctly_charged_after_more_than_one_month_passes() {
        savings.deposit(80);
        clock.pass(3, accounts);
        assertEquals(5.045035006875005, savings.getAccountBalance());
    }

    @Test
    void multiple_accounts_correctly_charged_after_more_than_one_month_passes() {
        savings.deposit(90);
        bank.create("checking", 87654321, 1.2);
        checking = bank.getAccount(87654321);
        checking.deposit(79);
        clock.pass(2, accounts);
        assertEquals(40.05251625, savings.getAccountBalance());
        assertEquals(29.083054, checking.getAccountBalance());
    }

    @Test
    void account_balance_doesnt_go_below_0_after_mbf_charged() {
        savings.deposit(99.99);
        clock.pass(5, accounts);
        assertEquals(0, savings.getAccountBalance());
    }

    @Test
    void accounts_close_if_mfb_charged_and_balance_gets_to_0() {
        savings.deposit(99.99);
        clock.pass(5, accounts);
        assertFalse(bank.containsIdNumber(12345678));
    }

    @Test
    void apr_calculated_correctly_for_one_month_passed() {
        savings.deposit(5000);
        clock.pass(1, accounts);
        assertEquals(5002.50, savings.getAccountBalance());
    }

    @Test
    void apr_calculated_correctly_for_multiple_months_passed() {
        savings.deposit(5000);
        clock.pass(2, accounts);
        assertEquals(5005.00125, savings.getAccountBalance());
    }

    @Test
    void apr_calculated_correctly_for_cd_account() {
        bank.create("cd", 00000001, 0.5, 200);
        cd = bank.getAccount(00000001);
        clock.pass(1, accounts);
        assertEquals(200.3335417245431, cd.getAccountBalance());
    }

    @Test
    void longevity_of_account_updated() {
        bank.create("checking", 87654321, 1.2);
        bank.deposit(87654321, 150);
        clock.pass(20, accounts);
        checking = bank.getAccount(87654321);
        assertEquals(20, checking.getLongevity());
    }
}
