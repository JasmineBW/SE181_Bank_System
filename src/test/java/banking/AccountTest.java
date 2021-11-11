package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AccountTest {
    public static final int SAVINGS_ID = 12345678;
    public static final double APR = 0.2;
    public static final int CHECKING_ID = 87654321;
    public static final int CD_ID = 01234567;
    Account savings;
    Account cd;
    Account checking;

    List<String> approvedAccounts = List.of("savings", "checking", "cd");

    @BeforeEach
    void setUp() {
        savings = new SavingsAccount("savings", SAVINGS_ID, 0.2);
        cd = new CDAccount("cd", 01234567, 1.2, 0);
        checking = new CheckingAccount("checking", 87654321, 1.2);
        //List<String> createdAccountsForTesting = List.of("savings", "checking", "cd");
    }

    @Test
    public void creating_new_savings_account() {
        SavingsAccount savings = new SavingsAccount("savings", SAVINGS_ID, 0.2);
        //assertEquals(SAVINGS_ID, savings.getID());
        assertEquals(0.2, savings.getAPR());
        assertTrue(approvedAccounts.contains(savings.getAccountType()));
    }

    @Test
    public void creating_new_checking_account() {
        CheckingAccount checking = new CheckingAccount("checking", 87654321, 1.2);
        //assertEquals(CHECKING_ID, checking.getID());
        assertEquals(1.2, checking.getAPR());
        assertTrue(approvedAccounts.contains(checking.getAccountType()));
    }

    @Test
    public void creating_new_cd_account() {
        CDAccount cd = new CDAccount("cd", 01234567, 1.2, 2000);
        //assertEquals(CD_ID, cd.getID());
        assertEquals(1.2, cd.getAPR());
        assertEquals(2000, cd.getAccountBalance());
        assertTrue(approvedAccounts.contains(cd.getAccountType()));
    }

    @Test
    public void savings_account_can_be_deposited_into() {
        savings.deposit(200);
        assertEquals(200, savings.getAccountBalance());
    }

    @Test
    public void checking_account_can_be_deposited_into() {
        checking.deposit(500);
        assertEquals(500, checking.getAccountBalance());
    }

    @Test
    public void cd_account_can_be_deposited_into() {
        cd.deposit(100);
        assertEquals(100, cd.getAccountBalance());
    }

    @Test
    public void savings_account_can_be_withdrawn_from() {
        savings.deposit(200);
        savings.withdraw(200);
        assertEquals(0, savings.getAccountBalance());
    }

    @Test
    public void checking_account_can_be_withdrawn_from() {
        checking.deposit(200);
        checking.withdraw(100);
        assertEquals(100, checking.getAccountBalance());
    }

    @Test
    public void cd_account_can_be_withdrawn_from() {
        cd.deposit(2000);
        cd.withdraw(1500);
        assertEquals(500, cd.getAccountBalance());
    }

    @Test
    public void savings_account_cannot_be_overdrawn_from() {
        System.out.println("banking.Account balance is " + savings.getAccountBalance());
        savings.withdraw(200);
        System.out.println("banking.Account balance is " + savings.getAccountBalance());
        assertEquals(0, savings.getAccountBalance());
    }

    @Test
    public void checking_account_cannot_be_overdrawn_from() {
        System.out.println("banking.Account balance is " + checking.getAccountBalance());
        checking.withdraw(200);
        System.out.println("banking.Account balance is " + checking.getAccountBalance());
        assertEquals(0, checking.getAccountBalance());
    }

    @Test
    public void cd_account_cannot_be_overdrawn_from() {
        System.out.println("banking.Account balance is " + cd.getAccountBalance());
        cd.withdraw(200);
        System.out.println("banking.Account balance is " + cd.getAccountBalance());
        assertEquals(0, cd.getAccountBalance());
    }

}
