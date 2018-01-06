package com.apiApp.CateringFacilityAPI.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class MenuItem extends BaseEntity {

    @NotNull
    @Column(length = 150)
    private String name;

    @NotNull
    private Double price;

    @Column(length = 5000)
    private String description;

    @NotNull
    private boolean onPromotion;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private Facility facility;

    public Facility getFacility() {
        return facility;
    }

    @JsonProperty
    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnPromotion() {
        return onPromotion;
    }

    public void setOnPromotion(boolean onPromotion) {
        this.onPromotion = onPromotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
