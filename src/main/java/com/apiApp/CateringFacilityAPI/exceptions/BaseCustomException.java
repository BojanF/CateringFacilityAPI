package com.apiApp.CateringFacilityAPI.exceptions;

public class BaseCustomException extends Exception {

    private String errorMessage;

    public BaseCustomException() {
        super();
    }

    public BaseCustomException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
