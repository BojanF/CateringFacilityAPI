package com.apiApp.CateringFacilityAPI.model.api;

public class ApiFacilityLocation {

    private String name;

    private String city;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ApiFacilityLocation(String name, String city, String address) {

        this.name = name;
        this.city = city;
        this.address = address;
    }
}
