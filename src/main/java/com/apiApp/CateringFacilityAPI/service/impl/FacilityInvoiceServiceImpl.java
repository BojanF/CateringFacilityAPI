package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityInvoiceRepository;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityRepository;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import com.apiApp.CateringFacilityAPI.service.ITaxAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityInvoiceServiceImpl implements IFacilityInvoiceService {

    private static int RELIEF_PERIOD = 7;

    @Autowired
    private IFacilityInvoiceRepository facilityInvoiceRepository;

    @Autowired
    private ITaxAmountService taxAmountService;

    @Autowired
    private IFacilityService facilityService;
    
    @Override
    public FacilityInvoice insertFacilityInvoice(SubscriptionPackage subscribe,
                                                 Facility facility) {

        LocalDateTime createdAt;
        List<FacilityInvoice> invoices = facilityService.facilityInvoices(facility.getId(),  new PageRequest(0, 1));
        if(invoices.size() == 0){
            //first subscription
            createdAt = LocalDateTime.now();
        }
        else{
            //not first subscription
            FacilityInvoice latestInvoice = invoices.get(0);
            LocalDateTime latestSubscriptionExpiryDate = latestInvoice.getCreatedAt().plusDays(latestInvoice
                    .getSubscribe()
                    .getExpiresIn()-1);
            if(LocalDateTime.now().isBefore(latestSubscriptionExpiryDate)){
                //new subscription before old one expires
                createdAt = latestSubscriptionExpiryDate.plusDays(1);
            }
            else{
                //new subscription after old one expires
                createdAt = LocalDateTime.now();
            }
        }

        FacilityInvoice facilityInvoice = new FacilityInvoice();
        facilityInvoice.setSubscribe(subscribe);
        facilityInvoice.setOriginalPackagePrice(subscribe.getPrice());
        facilityInvoice.setTaxAmount(taxAmountService.getTaxAmount());
        facilityInvoice.setCreatedAt(createdAt);
        facilityInvoice.setPayUntil(createdAt.plusDays(subscribe.getExpiresIn()-1+RELIEF_PERIOD));
        facilityInvoice.setFacility(facility);
        facilityInvoice.setGrossPrice();
        facilityInvoice.setInvoicePayed(false);

        if(facility.getStatus() == CustomerStatus.SUSPENDED){
            facility.setStatus(CustomerStatus.ACTIVE);
            facilityService.update(facility);
        }

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

    @Override
    public Double countAllFacilityInvoices() {
        return facilityInvoiceRepository.countAllFacilityInvoices();
    }

    @Override
    public Double countPaidFacilityInvoices() {
        return facilityInvoiceRepository.countPaidFacilityInvoices();
    }

    @Override
    public List<FacilityInvoice> notPaidInvoicesAfterReliefPeriod(LocalDateTime now) {
        return facilityInvoiceRepository.notPaidInvoicesAfterReliefPeriod(now);
    }

    @Override
    public Double sumOfFacilityInvoices(boolean paid) {
        return facilityInvoiceRepository.sumOfFacilityInvoices(paid);
    }

    @Override
    public List<Double> facilityInvoicesIncomeStats(){
        List<Double> result = new ArrayList<Double>();
        result.add(sumOfFacilityInvoices(true));
        result.add(sumOfFacilityInvoices(false));
        return  result;

    }


}
