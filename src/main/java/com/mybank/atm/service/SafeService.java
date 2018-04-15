package com.mybank.atm.service;

import com.mybank.atm.config.ErrorCodes;
import com.mybank.atm.config.MessageConstants;
import com.mybank.atm.entity.db.BankNote;
import com.mybank.atm.exception.ServiceException;
import com.mybank.atm.repo.SafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class SafeService {

    @Autowired
    SafeRepository safeRepository;

    public Integer getTotal() {
        Integer fiftyCount = safeRepository.getByBankNote(BankNote.FIFTY).getCount();
        Integer twentyCount = safeRepository.getByBankNote(BankNote.TWENTY).getCount();
        Integer tenCount = safeRepository.getByBankNote(BankNote.TEN).getCount();
        Integer fiveCount = safeRepository.getByBankNote(BankNote.FIVE).getCount();

        return (fiftyCount * 50) + (twentyCount * 20) + (tenCount * 10) + (fiveCount * 5);
    }

    public Map<BankNote, Integer> calculateNotes(Integer amount) throws ServiceException {
        Map<BankNote, Integer> cashMap = new EnumMap<>(BankNote.class);

        if(amount > getTotal()) {
            throw new ServiceException(ErrorCodes.ATM_FUNDS_ERROR, MessageConstants.ATM_FUNDS_WITHDRAWAL_ERROR);
        } else if((amount % 5) > 0) {
            throw new ServiceException(ErrorCodes.ATM_FUNDS_ERROR, MessageConstants.ATM_FUNDS_AMOUNT_ERROR);
        }

        Integer remainingAmount = amount;
        BankNote[] allNotes = {BankNote.FIFTY, BankNote.TWENTY, BankNote.TEN, BankNote.FIVE};
        for(BankNote note : allNotes) {
            Integer bankNotesRequired = remainingAmount / note.getValue();

            Integer bankNotesInSafe = safeRepository.getByBankNote(note).getCount();
            if(bankNotesInSafe < bankNotesRequired) {
                // Only this amount in the safe
                bankNotesRequired = bankNotesInSafe;
            }

            if(bankNotesRequired > 0) {
                remainingAmount = remainingAmount - (bankNotesRequired * note.getValue());
                cashMap.put(note, bankNotesRequired);
                if(remainingAmount == 0) {
                    // All notes counted
                    break;
                }
            }
        }

        if(remainingAmount > 0) {
            throw new ServiceException(ErrorCodes.ATM_FUNDS_ERROR, MessageConstants.ATM_NOTES_WITHDRAWAL_ERROR);
        }

        return cashMap;
    }

    /**
     * This setter method should be used only by unit tests.
     * @param safeRepository
     */
    protected void setSafeRepository(SafeRepository safeRepository) {
        this.safeRepository = safeRepository;
    }
}
