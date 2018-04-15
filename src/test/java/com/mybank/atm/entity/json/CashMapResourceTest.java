package com.mybank.atm.entity.json;

import com.mybank.atm.entity.db.BankNote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class CashMapResourceTest {

    @Test
    public void testCreateMap() {
        Map<BankNote, Integer> map = new HashMap<>();
        map.put(BankNote.FIFTY, 1);
        map.put(BankNote.TWENTY, 2);
        map.put(BankNote.TEN, 3);
        map.put(BankNote.FIVE, 4);
        CashMapResource cashMapResource = new CashMapResource(map);
        assertEquals("Incorrect value", cashMapResource.getFifty(), 1);
        assertEquals("Incorrect value", cashMapResource.getTwenty(), 2);
        assertEquals("Incorrect value", cashMapResource.getTen(), 3);
        assertEquals("Incorrect value", cashMapResource.getFive(), 4);
    }
}
