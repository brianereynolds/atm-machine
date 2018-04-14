package com.mybank.atm.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountTest {

    @Test
    public void testAccount() {
        Long testAccountNumber = 11223344L;
        String testPin = "3924";
        Account account = new Account(testAccountNumber, testPin);
        assertEquals("Account number incorrect", account.getAccountNumber(), testAccountNumber);
        assertEquals("Balance Incorrect", account.getBalance(), BigDecimal.valueOf(0));
        assertEquals("Overdraft incorrect", account.getOverdraft(), BigDecimal.valueOf(0));
        assertEquals("PIN Incorrect", account.getPin(), testPin);
        assertTrue("Invalid ID", account.getId() == 0);
    }

    @Test
    public void testAccountBasic() {
        Long testAccountNumber = 12991122L;
        BigDecimal testBalance = BigDecimal.valueOf(150.00);
        BigDecimal testOverdraft = BigDecimal.valueOf(50.99);
        String testPin = "0014";

        Account account = new Account();
        account.setAccountNumber(testAccountNumber);
        account.setBalance(testBalance);
        account.setOverdraft(testOverdraft);
        account.setPin(testPin);

        assertEquals("Account number incorrect", account.getAccountNumber(), testAccountNumber);
        assertEquals("Balance Incorrect", account.getBalance(), testBalance);
        assertEquals("Overdraft incorrect", account.getOverdraft(), testOverdraft);
        assertEquals("PIN Incorrect", account.getPin(), testPin);
        assertTrue("Invalid ID", account.getId() == 0);
    }

    @Test
    public void testAccountNulls() {
        Account account = new Account();
        account.setAccountNumber(null);
        account.setBalance(null);
        account.setOverdraft(null);
        account.setPin(null);

        assertNull("Account number incorrect", account.getAccountNumber());
        assertNull("Balance incorrect", account.getBalance());
        assertNull("O/D incorrect", account.getOverdraft());
        assertNull("PIN incorrect", account.getPin());
        assertTrue("Invalid ID", account.getId() == 0);
    }
}
