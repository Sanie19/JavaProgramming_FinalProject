package net.javaguides.banking.tests;

import net.javaguides.banking.entity.Card;
import net.javaguides.banking.entity.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest{
    @Test
    void testCustomerCreation() {
        Customer c = new Customer(1L, "Sanie", "sanie@gmail.com", "123", "070");

        assertEquals("Sanie", c.getName());
        assertEquals("sanie@gmail.com", c.getEmail());
    }


}