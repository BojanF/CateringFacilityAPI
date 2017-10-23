package com.apiApp.CateringFacilityAPI.model.jpa;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fac_location")
public class FacilityLocation extends BaseEntity{

    @NotNull
    private String country;

    @NotNull
    private String city;

    @NotNull
    private String address;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private Facility facility;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Facility getFacility() {
        return facility;
    }

    @JsonProperty
    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
