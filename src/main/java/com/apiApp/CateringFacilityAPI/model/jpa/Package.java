package com.apiApp.CateringFacilityAPI.model.jpa;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.sun.deploy.security.ValidationState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "packages")
public class Package extends BaseEntity{

    @NotNull
    private Long price;

    @NotNull
    private int expiresIn;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PackageStatus status;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

}
