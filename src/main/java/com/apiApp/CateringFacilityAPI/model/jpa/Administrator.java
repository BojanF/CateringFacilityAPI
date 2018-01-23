package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "administrators")
public class Administrator extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String surname;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
