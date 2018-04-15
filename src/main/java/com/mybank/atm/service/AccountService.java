package com.mybank.atm.service;

import com.mybank.atm.config.MessageConstants;
import com.mybank.atm.entity.Account;
import com.mybank.atm.exception.ServiceException;
import com.mybank.atm.repo.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;

    public BigDecimal getTotalFunds(Long accountNum) throws ServiceException {
        logger.debug("getTotalFunds: accountNum: {}", accountNum);
        Account account = getAccount(accountNum);
        return account.getBalance().add(account.getOverdraft());
    }

    public Account getAccount(Long accountNum) throws ServiceException {
        logger.debug("getAccount: accountNum: {}", accountNum);
        List<Account> accounts = accountRepository.findByAccountNumber(accountNum);
        if(accounts.isEmpty()) {
            throw new ServiceException(MessageConstants.MSG_ACCOUNT_NOT_FOUND);
        } else if(accounts.size() > 1) {
            logger.warn("validatePin: duplicate accounts found: {}", accounts.size());
        }
        return accounts.get(0);
    }

}
