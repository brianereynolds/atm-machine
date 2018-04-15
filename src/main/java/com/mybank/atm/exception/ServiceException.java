package com.mybank.atm.exception;

public class ServiceException extends Exception {
    private String code;
    private String message;

    public ServiceException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
