package com.mybank.atm.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountTest {

    private static final String MSG_BAD_ACCOUNT_NUMBER = "Account number incorrect";
    private static final String MSG_BAD_BALANCE = "Balance incorrect";
    private static final String MSG_BAD_OVERDRAFT = "Overdraft incorrect";
    private static final String MSG_BAD_PIN = "PIN incorrect";
    private static final String MSG_BAD_ID = "ID incorrect";

    @Test
    public void testAccount() {
        Long testAccountNumber = 11223344L;
        String testPin = "3924";
        Account account = new Account(testAccountNumber, testPin);
        assertEquals(MSG_BAD_ACCOUNT_NUMBER, account.getAccountNumber(), testAccountNumber);
        assertEquals(MSG_BAD_BALANCE, account.getBalance(), BigDecimal.valueOf(0));
        assertEquals(MSG_BAD_OVERDRAFT, account.getOverdraft(), BigDecimal.valueOf(0));
        assertEquals(MSG_BAD_PIN, account.getPin(), testPin);
        assertTrue(MSG_BAD_ID, account.getId() == 0);
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

        assertEquals(MSG_BAD_ACCOUNT_NUMBER, account.getAccountNumber(), testAccountNumber);
        assertEquals(MSG_BAD_BALANCE, account.getBalance(), testBalance);
        assertEquals(MSG_BAD_OVERDRAFT, account.getOverdraft(), testOverdraft);
        assertEquals(MSG_BAD_PIN, account.getPin(), testPin);
        assertTrue(MSG_BAD_ID, account.getId() == 0);
    }

    @Test
    public void testAccountNulls() {
        Account account = new Account();
        account.setAccountNumber(null);
        account.setBalance(null);
        account.setOverdraft(null);
        account.setPin(null);

        assertNull(MSG_BAD_ACCOUNT_NUMBER, account.getAccountNumber());
        assertNull(MSG_BAD_BALANCE, account.getBalance());
        assertNull(MSG_BAD_OVERDRAFT, account.getOverdraft());
        assertNull(MSG_BAD_PIN, account.getPin());
        assertTrue(MSG_BAD_ID, account.getId() == 0);
    }
}
