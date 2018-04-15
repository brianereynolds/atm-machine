package com.mybank.atm.exception;

public class ApiException extends Exception {
    private final String code;
    private final String message;

    public ApiException(ServiceException se) {
        this.code = se.getCode();
        this.message = se.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
