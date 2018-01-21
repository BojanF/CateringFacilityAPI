package com.apiApp.CateringFacilityAPI.model.api;

public class ApiMenuItemDetails extends ApiMenuItem {

    private String description;
    private ApiFacility facility;

    public ApiMenuItemDetails(Long id, String name, Double price, String description, Long facId, String facName) {
        super(id, name, price);
        this.description = description;
        this.facility = new ApiFacility(facId, facName);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApiFacility getFacility() {
        return facility;
    }

    public void setFacility(ApiFacility facility) {
        this.facility = facility;
    }
}
