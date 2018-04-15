package com.mybank.atm.service;

import com.mybank.atm.config.MessageConstants;
import com.mybank.atm.entity.Account;
import com.mybank.atm.exception.ServiceException;
import com.mybank.atm.repo.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PinService {
    private Logger logger = LoggerFactory.getLogger(PinService.class);

    @Autowired
    AccountRepository accountRepository;

    public boolean validatePin(Long accountNum, String pin) throws ServiceException {
        logger.debug("validatePin: accountNum: {}", accountNum);
        List<Account> accounts = accountRepository.findByAccountNumber(accountNum);
        if(accounts.isEmpty()) {
            throw new ServiceException(MessageConstants.MSG_ACCOUNT_NOT_FOUND);
        } else if(accounts.size() > 1) {
            logger.warn("validatePin: duplicate accounts found: {}", accounts.size());
        }
        return accounts.get(0).getPin().equals(pin);
    }
}
