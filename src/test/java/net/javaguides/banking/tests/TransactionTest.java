package net.javaguides.banking.tests;

import net.javaguides.banking.entity.Transaction;
import net.javaguides.banking.entity.TransactionType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    void testTransactionCreation() {
        Transaction t = new Transaction();
        t.setAmount(200);
        t.setType(TransactionType.DEPOSIT);
        t.setDate(LocalDateTime.now());

        assertEquals(200, t.getAmount());
        assertEquals(TransactionType.DEPOSIT, t.getType());
        assertNotNull(t.getDate());
    }
}
