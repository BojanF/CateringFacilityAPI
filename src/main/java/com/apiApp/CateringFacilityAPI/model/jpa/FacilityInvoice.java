package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fac_invoices")
public class FacilityInvoice extends Invoice{

    @NotNull
    @ManyToOne
    private Facility facility;

    @NotNull
    @OneToOne
    private Package subscribe;

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Package getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Package subscribe) {
        this.subscribe = subscribe;
    }
}
