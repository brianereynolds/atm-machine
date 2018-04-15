package com.mybank.atm.entity.json;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;

@JsonRootName(value = "Account")
public class AccountResource {

    private BigDecimal balance;
    private BigDecimal maxWithdrawal;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getMaxWithdrawal() {
        return maxWithdrawal;
    }

    public void setMaxWithdrawal(BigDecimal maxWithdrawal) {
        this.maxWithdrawal = maxWithdrawal;
    }
}
