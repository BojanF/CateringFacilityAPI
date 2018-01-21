package com.apiApp.CateringFacilityAPI.model.api;

import java.util.List;

public class ApiFacilityDetails extends ApiFacility {

    private List<ApiFacilityLocation> locations;

    public ApiFacilityDetails(Long id, String name, List<ApiFacilityLocation> locations) {
        super(id, name);
        this.locations = locations;
    }

    public List<ApiFacilityLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<ApiFacilityLocation> locations) {
        this.locations = locations;
    }
}
