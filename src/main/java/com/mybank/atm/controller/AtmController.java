package com.mybank.atm.controller;

import com.mybank.atm.config.ErrorCodes;
import com.mybank.atm.config.MessageConstants;
import com.mybank.atm.entity.db.Account;
import com.mybank.atm.entity.json.AccountResource;
import com.mybank.atm.entity.json.ErrorResource;
import com.mybank.atm.exception.ApiException;
import com.mybank.atm.exception.ServiceException;
import com.mybank.atm.service.AccountService;
import com.mybank.atm.service.PinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AtmController {

    private Logger logger = LoggerFactory.getLogger(AtmController.class);

    @Autowired
    private PinService pinService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, value = "/balance/{accountNum}/{pin}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public AccountResource getBalance(@PathVariable Long accountNum, @PathVariable String pin) throws ApiException {
        AccountResource accountResource = new AccountResource();

        try {
            if(pinService.validatePin(accountNum, pin) == false) {
                throw new ServiceException(ErrorCodes.PIN_VALIDATION, MessageConstants.INVALID_PIN);
            }

            Account account = accountService.getAccount(accountNum);
            BigDecimal accountBalance = account.getBalance();
            accountResource.setBalance(accountBalance);

            // TODO: Also Limit this by the contents of the ATM
            accountResource.setMaxWithdrawal(accountBalance.add(account.getOverdraft()));
        } catch(ServiceException se) {
            logger.error("getBalance failed for {}", accountNum);
            throw new ApiException(se);
        }

        return accountResource;
    }

    @ExceptionHandler(ApiException.class)
    public ErrorResource handleApiException(ApiException e) {
        return new ErrorResource(e.getCode(), e.getMessage());
    }
}
