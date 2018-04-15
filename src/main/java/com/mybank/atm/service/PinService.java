package com.mybank.atm.service;

import com.mybank.atm.config.ErrorCodes;
import com.mybank.atm.config.MessageConstants;
import com.mybank.atm.entity.db.Account;
import com.mybank.atm.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

@Service
public class PinService {
    private Logger logger = LoggerFactory.getLogger(PinService.class);

    @Autowired
    AccountService accountService;

    public boolean validatePin(Long accountNum, String pin) throws ServiceException {
        if(pin.isEmpty()) {
            logger.error("validatePin: Empty PIN");
            throw new ServiceException(ErrorCodes.INVALID_PIN, MessageConstants.INVALID_PIN);
        }
        logger.debug("validatePin: accountNum: {}, pin: {}", accountNum, pin.replaceAll(".", "\\*"));
        Account account = accountService.getAccount(accountNum);
        return account.getPin().equals(pin);
    }
}
