package com.apiApp.CateringFacilityAPI.model.api;

import java.util.List;

public class ApiFacilityLocationContacts {

    private ApiFacilityLocation location;
    private List<String> contacts;

    public ApiFacilityLocationContacts(ApiFacilityLocation location, List<String> contacts) {
        this.location= location;
        this.contacts = contacts;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public ApiFacilityLocation getLocation() {
        return location;
    }

    public void setLocation(ApiFacilityLocation location) {
        this.location = location;
    }
}
