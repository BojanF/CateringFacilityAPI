package com.apiApp.CateringFacilityAPI.custom;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;

public class SubscriptionPackageStats {

    private Long id;

    private String name;

    private Double sumOfPaidInvoices;

    private Double sumOfUnpaidInvoices;

    private PackageStatus status;

    public SubscriptionPackageStats(Long id,
                                    String name,
                                    Double sumOfPaidInvoices,
                                    Double sumOfUnpaidInvoices,
                                    PackageStatus status) {
        this.id = id;
        this.name = name;
        this.sumOfPaidInvoices = sumOfPaidInvoices;
        this.sumOfUnpaidInvoices = sumOfUnpaidInvoices;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSumOfPaidInvoices() {
        return sumOfPaidInvoices;
    }

    public void setSumOfPaidInvoices(Double sumOfPaidInvoices) {
        this.sumOfPaidInvoices = sumOfPaidInvoices;
    }

    public Double getSumOfUnpaidInvoices() {
        return sumOfUnpaidInvoices;
    }

    public void setSumOfUnpaidInvoices(Double sumOfUnpaidInvoices) {
        this.sumOfUnpaidInvoices = sumOfUnpaidInvoices;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }
}
