package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;

import java.time.LocalDateTime;
import java.util.List;

public interface IApiInvoiceService {

    ApiInvoice insertApiInvoice(SubscriptionPackage subscribe, LocalDateTime createdAt, Developer developer);

    ApiInvoice findOne(Long id);

    ApiInvoice update(ApiInvoice apiInvoice);

    void delete(Long id);

    Iterable<ApiInvoice> findAll();

    List<ApiInvoice> getAllInvoicesSortedByCreatedAt();
    
}
