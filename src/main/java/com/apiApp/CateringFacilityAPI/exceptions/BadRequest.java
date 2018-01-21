package com.apiApp.CateringFacilityAPI.exceptions;

public class BadRequest extends BaseCustomException {

    public BadRequest(String errorMessage) {
        super(errorMessage);
    }
}
