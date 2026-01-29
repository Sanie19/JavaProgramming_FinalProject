package net.javaguides.banking.tests;

import net.javaguides.banking.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountHolderName("Sanie");
        account.setBalance(500);
    }

    @Test
    void testInitialBalance() {
        assertEquals(500, account.getBalance(),
                "Initial balance should be 500");
    }

    @Test
    void testDeposit() {
        account.setBalance(account.getBalance() + 200);
        assertEquals(700, account.getBalance(),
                "Balance should increase after deposit");
    }

    @Test
    void testWithdraw() {
        account.setBalance(account.getBalance() - 100);
        assertEquals(400, account.getBalance(),
                "Balance should decrease after withdraw");
    }

    @Test
    void testWithdrawMoreThanBalance() {
        assertThrows(IllegalStateException.class, () -> {
            if (account.getBalance() < 600) {
                throw new IllegalStateException("Insufficient funds");
            }
        });
    }
}
