package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    public static final String SAVINGS = "savings";
    public static final int SAVINGS_ID = 12345678;
    public static final double APR = 0.2;
    public static final String CHECKING = "checking";
    public static final int CHECKING_ID = 87654321;
    public static final String CD = "cd";
    public static final int CD_ID = 01234567;
    Bank bank;
    private Account savings, checking;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        Clock.monthsPassed = 0;
    }

    @Test
    public void bank_has_no_accounts() {
        assertTrue(bank.getListOfAccounts().isEmpty());
    }

    @Test
    public void add_savings_account_to_bank() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        assertEquals(bank.getListOfAccounts().size(), 1);
        assertTrue(bank.getListOfAccounts().containsKey(SAVINGS_ID));
        assertTrue(bank.getListOfAccounts().get(SAVINGS_ID) instanceof SavingsAccount);
        assertEquals(SAVINGS_ID, bank.getListOfAccounts().get(SAVINGS_ID).getID());
        System.out.println("the accounts are:" + bank.getListOfAccounts());

    }

    @Test
    public void add_checking_account_to_bank() {
        bank.create(CHECKING, CHECKING_ID, 0.5);
        assertEquals(1, bank.getListOfAccounts().size());
        assertTrue(bank.getListOfAccounts().containsKey(CHECKING_ID));
        assertTrue(bank.getListOfAccounts().get(CHECKING_ID) instanceof CheckingAccount);
        assertEquals(CHECKING_ID, bank.getListOfAccounts().get(CHECKING_ID).getID());
        System.out.println("the accounts are:" + bank.getListOfAccounts());

    }

    @Test
    public void add_cd_account_to_bank() {
        bank.create(CD, CD_ID, 1.2, 2000);
        assertEquals(1, bank.getListOfAccounts().size());
        assertTrue(bank.getListOfAccounts().get(CD_ID) instanceof CDAccount);
        assertEquals(CD_ID, bank.getListOfAccounts().get(CD_ID).getID());
        System.out.println("the accounts are:" + bank.getListOfAccounts());
    }

    @Test
    public void add_two_accounts_of_different_types_to_bank() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.create(CHECKING, 24681012, 1.5);
        assertEquals(2, bank.getListOfAccounts().size());
        assertTrue(bank.getListOfAccounts().containsKey(SAVINGS_ID));
        assertTrue(bank.getListOfAccounts().containsKey(24681012));
        assertFalse(bank.getListOfAccounts().get(24681012) instanceof SavingsAccount);
        System.out.println("the accounts are:" + bank.getListOfAccounts());
    }

    @Test
    public void deposit_into_savings_account() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 2000);
        assertEquals(2000, bank.getListOfAccounts().get(SAVINGS_ID).getAccountBalance());
    }

    @Test
    public void deposit_twice_into_savings_account() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 2000);
        bank.deposit(SAVINGS_ID, 500);
        assertEquals(2500, bank.getListOfAccounts().get(SAVINGS_ID).getAccountBalance());
    }

    @Test
    public void deposit_into_checking_account() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 2000);
        assertEquals(2000, bank.getListOfAccounts().get(CHECKING_ID).getAccountBalance());
    }

    @Test
    public void deposit_twice_into_checking_account() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 300);
        bank.deposit(CHECKING_ID, 40);
        assertEquals(340, bank.getListOfAccounts().get(CHECKING_ID).getAccountBalance());
    }

    @Test
    public void deposit_into_cd_account() {
        bank.create(CD, CD_ID, APR, 0);
        bank.deposit(CD_ID, 2000);
        assertEquals(2000, bank.getListOfAccounts().get(CD_ID).getAccountBalance());
    }

    @Test
    public void deposit_$0_in_savings_account() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 0);
        assertTrue(bank.getListOfAccounts().get(SAVINGS_ID).getAccountBalance() == 0);
    }

    @Test
    public void deposit_$0_in_checking_account() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 0);
        assertTrue(bank.getListOfAccounts().get(CHECKING_ID).getAccountBalance() == 0);
    }

    @Test
    public void deposit_$0_in_cd_account() {
        bank.create(CD, CD_ID, APR, 0);
        bank.deposit(CD_ID, 0);
        assertTrue(bank.getListOfAccounts().get(CD_ID).getAccountBalance() == 0);
    }

    @Test
    public void savings_account_in_bank_can_be_withdrawn_from() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 2);
        bank.withdraw(SAVINGS_ID, 2.5f);
        assertEquals(0, bank.getListOfAccounts().get(SAVINGS_ID).getAccountBalance());
    }

    @Test
    public void savings_account_can_be_withdrawn_twice_from() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 2);
        bank.withdraw(SAVINGS_ID, 2.5f);
        bank.withdraw(SAVINGS_ID, 50);
        assertEquals(0, bank.getListOfAccounts().get(SAVINGS_ID).getAccountBalance());
    }

    @Test
    public void checking_account_in_bank_can_be_withdrawn_from() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 50);
        bank.withdraw(CHECKING_ID, 10);
        assertEquals(40, bank.getListOfAccounts().get(CHECKING_ID).getAccountBalance());
    }

    @Test
    public void checking_account_be_withdrawn_twice_from() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 50);
        bank.withdraw(CHECKING_ID, 10);
        bank.withdraw(CHECKING_ID, 5);
        assertEquals(35, bank.getListOfAccounts().get(CHECKING_ID).getAccountBalance());
    }

    @Test
    public void cd_account_in_bank_can_be_withdrawn_from() {
        bank.create(CD, CD_ID, APR, 0);
        bank.deposit(CD_ID, 2000);
        bank.withdraw(CD_ID, 3000);
        assertEquals(0, bank.getListOfAccounts().get(CD_ID).getAccountBalance());
    }

    @Test
    public void $0_can_be_withdrawn_from_savings_account() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 2000);
        bank.withdraw(SAVINGS_ID, 0);
        assertTrue(bank.getListOfAccounts().get(SAVINGS_ID).getAccountBalance() == 2000);
    }

    @Test
    public void $0_can_be_withdrawn_from_checking_account() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 30);
        bank.withdraw(CHECKING_ID, 0);
        assertTrue(bank.getListOfAccounts().get(CHECKING_ID).getAccountBalance() == 30);
    }

    @Test
    public void $0_can_be_withdrawn_from_cd_account() {
        bank.create(CD, CD_ID, APR, 0);
        bank.deposit(CD_ID, 50);
        bank.withdraw(CD_ID, 0);
        assertTrue(bank.getListOfAccounts().get(CD_ID).getAccountBalance() == 50);
    }

    @Test
    public void savings_account_balance_doesnt_drop_below_0() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.withdraw(SAVINGS_ID, 20);
        assertTrue(bank.getListOfAccounts().get(SAVINGS_ID).getAccountBalance() == 0);
    }

    @Test
    public void checking_account_balance_doesnt_drop_below_0() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.withdraw(CHECKING_ID, 200);
        assertTrue(bank.getListOfAccounts().get(CHECKING_ID).getAccountBalance() == 0);
    }

    @Test
    public void cd_account_balance_doesnt_drop_below_0() {
        bank.create(CD, CD_ID, APR, 0);
        bank.withdraw(CD_ID, 500);
        assertTrue(bank.getListOfAccounts().get(CD_ID).getAccountBalance() == 0);
    }

    @Test
    public void pass_increments_months_passed() {
        bank.pass(5);
        //System.out.println(bank.clock.getMonthsPassed());
        assertTrue(bank.clock.getMonthsPassed() == 5);
    }

    @Test
    void pass_command_can_be_called_twice_consecutively() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(87654321, 200);
        bank.pass(10);
        bank.pass(5);
        assertTrue(bank.getAccount(87654321).getLongevity() == 15);

    }

    @Test
    public void pass_makes_checking_and_savings_account_accrue_APR_over_months() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(CHECKING_ID, 200);
        bank.deposit(SAVINGS_ID, 100);
        bank.pass(5);
        assertTrue(bank.getAccount(CHECKING_ID).getAccountBalance() > 200);
        assertTrue(bank.getAccount(SAVINGS_ID).getAccountBalance() > 100);
    }

    @Test
    public void cd_account_accrue_APR_over_months() {
        bank.create(CD, CD_ID, 2.3, 500);
        bank.pass(10);
        assertTrue(bank.getAccount(CD_ID).getAccountBalance() > 500);
    }

    @Test
    void savings_account_can_be_transferred_from() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 50);
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.transfer(SAVINGS_ID, CHECKING_ID, 10);
        assertEquals(40, bank.getAccount(SAVINGS_ID).getAccountBalance());
    }

    @Test
    void savings_account_can_be_transferred_to() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 20);
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 60);
        bank.transfer(CHECKING_ID, SAVINGS_ID, 20);
        assertEquals(40, bank.getAccount(SAVINGS_ID).getAccountBalance());
    }

    @Test
    void savings_account_can_be_transferred_twice_to() {
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 20);
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 60);
        bank.transfer(CHECKING_ID, SAVINGS_ID, 20);
        bank.transfer(CHECKING_ID, SAVINGS_ID, 1);
        assertEquals(41, bank.getAccount(SAVINGS_ID).getAccountBalance());
    }

    @Test
    void checking_account_can_be_transferred_from() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 200);
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.transfer(CHECKING_ID, SAVINGS_ID, 100);
        assertEquals(100, bank.getAccount(CHECKING_ID).getAccountBalance());
    }

    @Test
    void checking_account_can_be_transferred_twice_from() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 200);
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.transfer(CHECKING_ID, SAVINGS_ID, 100);
        bank.transfer(CHECKING_ID, SAVINGS_ID, 90);
        assertEquals(10, bank.getAccount(CHECKING_ID).getAccountBalance());
    }

    @Test
    void checking_account_can_be_transferred_to() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 200);
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 5);
        bank.transfer(SAVINGS_ID, CHECKING_ID, 5);
        assertEquals(205, bank.getAccount(CHECKING_ID).getAccountBalance());
    }

    @Test
    void checking_account_can_be_transferred_twice_to() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.deposit(CHECKING_ID, 200);
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 5);
        bank.transfer(SAVINGS_ID, CHECKING_ID, 5);
        bank.transfer(SAVINGS_ID, CHECKING_ID, 0);
        assertEquals(205, bank.getAccount(CHECKING_ID).getAccountBalance());
    }

    @Test
    void account_balance_less_than_amount_to_be_transferred() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.deposit(SAVINGS_ID, 150);
        bank.transfer(SAVINGS_ID, CHECKING_ID, 170);
        assertEquals(0, bank.getAccount(SAVINGS_ID).getAccountBalance());
        assertEquals(150, bank.getAccount(CHECKING_ID).getAccountBalance());
    }

    @Test
    void $0_can_be_transferred_to_from_account() {
        bank.create(CHECKING, CHECKING_ID, APR);
        bank.create(SAVINGS, SAVINGS_ID, APR);
        bank.transfer(SAVINGS_ID, CHECKING_ID, 0);
        assertEquals(bank.getAccount(CHECKING_ID).getAccountBalance(), bank.getAccount(SAVINGS_ID).getAccountBalance());
    }
}

