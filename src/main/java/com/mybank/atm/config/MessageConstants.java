package com.mybank.atm.config;

public class MessageConstants {

    private MessageConstants() {
        // Hide implicit constructor
    }

    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String INVALID_PIN = "Invalid PIN provided";
    public static final String ATM_FUNDS_WITHDRAWAL_ERROR = "Insufficient ATM funds";
    public static final String ATM_FUNDS_AMOUNT_ERROR = "Cannot dispense this amount";
    public static final String ATM_NOTES_WITHDRAWAL_ERROR = "Insufficient ATM notes to complete withdrawal";
}
