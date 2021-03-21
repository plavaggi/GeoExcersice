package com.exercise.geo.exception;

public class CountryDataServiceException extends RuntimeException{

	private static final long serialVersionUID = -7750633312843148074L;
	private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CountryDataServiceException(int code, String message) {
        super(message);
        this.setCode(code);
    }

}
