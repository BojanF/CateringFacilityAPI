package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "api_invoice")
public class ApiInvoice extends Invoice {

    @NotNull
    @ManyToOne
    private Developer developer;

    @NotNull
    @OneToOne
    private Package subscribe;

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Package getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Package subscribe) {
        this.subscribe = subscribe;
    }
}
