package com.exercise.geo.exception;

public class NotRecognizeIpException extends RuntimeException{
    private static final long serialVersionUID = -8087974755572654799L;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public NotRecognizeIpException(int code, String message) {
        super(message);
        this.setCode(code);
    }
}
