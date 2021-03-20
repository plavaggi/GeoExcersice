package com.exercise.geo.exception;

public class ExchangeException extends RuntimeException{
    private static final long serialVersionUID = -4674069108333467772L;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ExchangeException(String code, String message) {
        super(message);
        this.setCode(code);
    }
}
