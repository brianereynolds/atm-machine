package com.mybank.atm.config;

public class ErrorCodes {

    private ErrorCodes() {
        // Hide implicit constructor
    }

    public static final String PIN_VALIDATION = "P1001";
    public static final String INVALID_PIN = "P1002";
    public static final String ACCOUNT_LOOKUP = "A1001";
    public static final String ATM_FUNDS_ERROR = "S1001";
}
