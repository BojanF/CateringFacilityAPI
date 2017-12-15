package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityInvoiceRepository;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import com.apiApp.CateringFacilityAPI.service.ITaxAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacilityInvoiceServiceImpl implements IFacilityInvoiceService {
    
    @Autowired
    private IFacilityInvoiceRepository facilityInvoiceRepository;

    @Autowired
    private ITaxAmountService taxAmountService;
    
    @Override
    public FacilityInvoice insertFacilityInvoice(SubscriptionPackage subscribe, LocalDateTime createdAt, Facility facility) {
        FacilityInvoice facilityInvoice = new FacilityInvoice();
        facilityInvoice.setSubscribe(subscribe);
        facilityInvoice.setOriginalPackagePrice(subscribe.getPrice());
        facilityInvoice.setTaxAmount(taxAmountService.getTaxAmount());
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

    @Override
    public List<FacilityInvoice> getAllFacilityInvoicesSortedByCreatedAt(){
        return facilityInvoiceRepository.getAllFacilityInvoicesSortedByCreatedAt();
    }
}
