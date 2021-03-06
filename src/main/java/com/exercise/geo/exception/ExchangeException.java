package com.exercise.geo.exception;

public class ExchangeException extends RuntimeException{

	private static final long serialVersionUID = 101262531593930943L;
	private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ExchangeException(int code, String message) {
        super(message);
        this.setCode(code);
    }
}
