package net.javaguides.banking.tests;

import net.javaguides.banking.entity.Card;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void testCardCreation() {
        Card card = new Card();
        card.setCardNumber("4539123412345678");
        card.setCardHolderName("Sanie");
        card.setExpiryDate(LocalDate.of(2028, 6, 30));

        assertEquals("4539123412345678", card.getCardNumber());
        assertEquals("Sanie", card.getCardHolderName());
        assertEquals(2028, card.getExpiryDate().getYear());
    }
}
