package com.apiApp.CateringFacilityAPI.model.jpa;

import com.apiApp.CateringFacilityAPI.model.enums.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Customer extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @NotNull
    private boolean usedTrial;

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public boolean getUsedTrial() {
        return usedTrial;
    }

    public void setUsedTrial(boolean usedTrial) {
        this.usedTrial = usedTrial;
    }
}
