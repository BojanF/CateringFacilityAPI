package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "facilities")
public class Facility extends Customer{

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
