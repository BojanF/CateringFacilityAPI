package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
public class Invoice extends BaseEntity {

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime payedAt;

    @NotNull
    private boolean invoicePayed;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(LocalDateTime payedAt) {
        this.payedAt = payedAt;
    }

    public boolean getStatus() {
        return invoicePayed;
    }

    public void setStatus(boolean invoicePayed) {
        this.invoicePayed = invoicePayed;
    }
}
