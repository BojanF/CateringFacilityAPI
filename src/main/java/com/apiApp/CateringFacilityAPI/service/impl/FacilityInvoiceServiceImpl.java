package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityInvoiceRepository;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FacilityInvoiceServiceImpl implements IFacilityInvoiceService {
    
    @Autowired
    private IFacilityInvoiceRepository facilityInvoiceRepository;
    
    @Override
    public FacilityInvoice insertFacilityInvoice(SubscriptionPackage subscribe, Double taxAmount, LocalDateTime createdAt, Facility facility) {
        FacilityInvoice facilityInvoice = new FacilityInvoice();
        facilityInvoice.setSubscribe(subscribe);
        facilityInvoice.setTaxAmount(taxAmount);
        facilityInvoice.setCreatedAt(createdAt);
        facilityInvoice.setFacility(facility);
        facilityInvoice.setGrossPrice();
        facilityInvoice.setInvoicePayed(false);
        return facilityInvoiceRepository.save(facilityInvoice);
    }

    @Override
    public FacilityInvoice findOne(Long id) {
        return facilityInvoiceRepository.findOne(id);
    }

    @Override
    public FacilityInvoice update(FacilityInvoice facilityInvoice) {
        return facilityInvoiceRepository.save(facilityInvoice);
    }

    @Override
    public void delete(Long id) {
        facilityInvoiceRepository.delete(id);
    }

    @Override
    public Iterable<FacilityInvoice> findAll() {
        return facilityInvoiceRepository.findAll();
    }
}
