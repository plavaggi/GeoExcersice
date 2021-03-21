package com.exercise.geo.exception;

public class BadRequestIpSearchException extends RuntimeException {
    private static final long serialVersionUID = 4674069108333467772L;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BadRequestIpSearchException(int code, String message) {
        super(message);
        this.setCode(code);
    }
}
