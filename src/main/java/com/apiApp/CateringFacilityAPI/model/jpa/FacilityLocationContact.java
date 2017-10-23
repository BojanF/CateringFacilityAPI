package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fac_loc_contact")
public class FacilityLocationContact {

    @EmbeddedId
    private LocationContactId id;

    @ManyToOne
    @MapsId("facilityLocationId")
    private FacilityLocation location;

    public LocationContactId getId() {
        return id;
    }

    public void setId(LocationContactId id) {
        this.id = id;
    }


    public FacilityLocation getLocation() {
        return location;
    }

    public void setLocation(FacilityLocation location) {
        this.location = location;
    }
}
