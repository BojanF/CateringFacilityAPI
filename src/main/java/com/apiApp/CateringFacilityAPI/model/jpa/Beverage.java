package com.apiApp.CateringFacilityAPI.model.jpa;

import com.apiApp.CateringFacilityAPI.model.enums.BeverageType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "beverages")
public class Beverage extends MenuItem{

    @NotNull
    @Enumerated(EnumType.STRING)
    private BeverageType type;

    public BeverageType getType() {
        return type;
    }

    public void setType(BeverageType type) {
        this.type = type;
    }
}
