package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.persistance.IApiInvoiceRepository;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApiInvoiceServiceImpl implements IApiInvoiceService {

    @Autowired
    private IApiInvoiceRepository apiInvoiceRepository;

    @Override
    public ApiInvoice insertApiInvoice(SubscriptionPackage subscribe, Double taxAmount, LocalDateTime createdAt, Developer developer) {
        ApiInvoice apiInvoice = new ApiInvoice();
        apiInvoice.setSubscribe(subscribe);
        apiInvoice.setTaxAmount(taxAmount);
        apiInvoice.setCreatedAt(createdAt);
        apiInvoice.setDeveloper(developer);
        apiInvoice.setGrossPrice();
        apiInvoice.setInvoicePayed(false);
        return apiInvoiceRepository.save(apiInvoice);
    }

    @Override
    public ApiInvoice findOne(Long id) {
        return apiInvoiceRepository.findOne(id);
    }

    @Override
    public ApiInvoice update(ApiInvoice apiInvoice) {
        return apiInvoiceRepository.save(apiInvoice);
    }

    @Override
    public void delete(Long id) {
        apiInvoiceRepository.delete(id);
    }

    @Override
    public Iterable<ApiInvoice> findAll() {
        return apiInvoiceRepository.findAll();
    }
}
