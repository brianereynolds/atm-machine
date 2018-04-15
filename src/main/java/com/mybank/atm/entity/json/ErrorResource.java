package com.mybank.atm.entity.json;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "Error")
public class ErrorResource {
    private String code;
    private String message;

    public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
