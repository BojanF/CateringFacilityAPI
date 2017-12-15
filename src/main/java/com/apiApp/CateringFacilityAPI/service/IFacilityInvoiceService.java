package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;

import java.time.LocalDateTime;
import java.util.List;

public interface IFacilityInvoiceService {

    FacilityInvoice insertFacilityInvoice(SubscriptionPackage subscribe, LocalDateTime createdAt, Facility facility);

    FacilityInvoice findOne(Long id);

    FacilityInvoice update(FacilityInvoice facilityInvoice);

    void delete(Long id);

    Iterable<FacilityInvoice> findAll();

    List<FacilityInvoice> getAllFacilityInvoicesSortedByCreatedAt();
}
