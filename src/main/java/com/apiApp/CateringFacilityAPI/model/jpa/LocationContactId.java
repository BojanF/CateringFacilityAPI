package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LocationContactId implements Serializable {

    @NotNull
    private Long facilityLocationId;

    @NotNull
    @Column(name="tel_number")
    private String telephoneNumber;


    public LocationContactId(Long facilityLocationId, String telephoneNumber) {
        this.facilityLocationId = facilityLocationId;
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        LocationContactId that = (LocationContactId) o;
        return Objects.equals(facilityLocationId, that.facilityLocationId) &&
                Objects.equals(telephoneNumber, that.telephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facilityLocationId, telephoneNumber);
    }

    public Long getFacilityLocationId() {
        return facilityLocationId;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

}
