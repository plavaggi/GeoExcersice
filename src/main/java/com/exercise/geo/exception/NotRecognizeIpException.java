package com.exercise.geo.exception;

public class NotRecognizeIpException extends RuntimeException{
    private static final long serialVersionUID = -8087974755572654799L;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NotRecognizeIpException(String code, String message) {
        super(message);
        this.setCode(code);
    }
}
