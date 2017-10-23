package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "developers")
public class Developer extends Customer{

    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
