package com.mybank.atm.repo;

import com.mybank.atm.BankNote;
import com.mybank.atm.entity.Safe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class SafeRepositoryTest {

    private final String MSG_INVALID_50_NOTES = "Invalid number of 50 notes";
    private final String MSG_INVALID_20_NOTES = "Invalid number of 20 notes";
    private final String MSG_INVALID_10_NOTES = "Invalid number of 10 notes";
    private final String MSG_INVALID_5_NOTES = "Invalid number of 5 notes";

    @Autowired
    private SafeRepository safeRepository;

    @Test
    public void testGetNotes(){
        Safe safeFifty = safeRepository.getByBankNote(BankNote.FIFTY);
        assertEquals(MSG_INVALID_50_NOTES, safeFifty.getCount(), 20);

        Safe safeTwenty = safeRepository.getByBankNote(BankNote.TWENTY);
        assertEquals(MSG_INVALID_20_NOTES, safeTwenty.getCount(), 30);

        Safe safeTenner = safeRepository.getByBankNote(BankNote.TWENTY);
        assertEquals(MSG_INVALID_10_NOTES, safeTenner.getCount(), 30);

        Safe safeFiver = safeRepository.getByBankNote(BankNote.TWENTY);
        assertEquals(MSG_INVALID_5_NOTES, safeFiver.getCount(), 20);
    }


}
