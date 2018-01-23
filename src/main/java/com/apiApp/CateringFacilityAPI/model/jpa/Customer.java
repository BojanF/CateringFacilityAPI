package com.apiApp.CateringFacilityAPI.model.jpa;

import com.apiApp.CateringFacilityAPI.model.enums.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Customer extends BaseEntity {

    @NotNull
    @OneToOne
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isUsedTrial() {
        return usedTrial;
    }
}
