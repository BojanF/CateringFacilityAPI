package com.apiApp.CateringFacilityAPI.model.api;

public class ApiMenuItemDetailsTyped extends ApiMenuItemDetails {

    private String type;

    public ApiMenuItemDetailsTyped(Long id,
                                   String name,
                                   Double price,
                                   String description,
                                   Long facId,
                                   String facName,
                                   String type) {
        super(id, name, price, description, facId, facName);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
