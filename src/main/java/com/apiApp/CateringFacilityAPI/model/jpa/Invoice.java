package com.apiApp.CateringFacilityAPI.model.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
public class Invoice extends BaseEntity {

    @NotNull
    @OneToOne
    private SubscriptionPackage subscribe;

    @NotNull
    private Double grossPrice;

    @NotNull
    private Double taxAmount;

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

    public boolean getPayedStatus() {
        return invoicePayed;
    }

    public void setPayedStatus(boolean invoicePayed) {
        this.invoicePayed = invoicePayed;
    }

    public SubscriptionPackage getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(SubscriptionPackage subscribe) {
        this.subscribe = subscribe;
    }

    public Double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice() {
        this.grossPrice = (1+this.taxAmount) * this.subscribe.getPrice();
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public boolean isInvoicePayed() {
        return invoicePayed;
    }

    public void setInvoicePayed(boolean invoicePayed) {
        this.invoicePayed = invoicePayed;
    }
}
