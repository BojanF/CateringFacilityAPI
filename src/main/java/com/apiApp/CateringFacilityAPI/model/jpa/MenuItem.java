package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class MenuItem extends BaseEntity {

    @NotNull
    private Long price;

    private String description;

    @NotNull
    private boolean onPromotion;

    @NotNull
    @ManyToOne
    private Facility facility;

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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
}
