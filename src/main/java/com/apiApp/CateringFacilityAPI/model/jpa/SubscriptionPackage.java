package com.apiApp.CateringFacilityAPI.model.jpa;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.sun.deploy.security.ValidationState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "packages")
public class SubscriptionPackage extends BaseEntity{

    @NotNull
    private Double price;

    @NotNull
    private int expiresIn;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PackageStatus status;

    @Column(length = 5000)
    public String description;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
