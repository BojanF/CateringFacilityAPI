package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.persistance.IApiInvoiceRepository;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.ITaxAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiInvoiceServiceImpl implements IApiInvoiceService {

    private static int RELIEF_PERIOD = 7;

    @Autowired
    private IApiInvoiceRepository apiInvoiceRepository;

    @Autowired
    private ITaxAmountService taxAmountService;

    @Autowired
    private IDeveloperService developerService;

    @Override
    public ApiInvoice insertApiInvoice(SubscriptionPackage subscribe,
                                       Developer developer) {

        LocalDateTime createdAt;
        List<ApiInvoice> invoices = developerService.developerInvoices(developer.getId(), new PageRequest(0, 1));
        if(invoices.size() == 0){
            //first subscription
            createdAt = LocalDateTime.now();
        }
        else{
            //not first subscription
            ApiInvoice latestInvoice = invoices.get(0);
            LocalDateTime latestSubscriptionExpiryDate = latestInvoice.getCreatedAt().plusDays(latestInvoice.
                    getSubscribe().
                    getExpiresIn()-1);
            if(LocalDateTime.now().isBefore(latestSubscriptionExpiryDate)){
                //new subscription before old one expires
                createdAt = latestSubscriptionExpiryDate.plusDays(1);
            }
            else{
                //new subscription after old one expires
                createdAt = LocalDateTime.now();
            }
        }

        ApiInvoice apiInvoice = new ApiInvoice();
        apiInvoice.setSubscribe(subscribe);
        apiInvoice.setOriginalPackagePrice(subscribe.getPrice());
        apiInvoice.setTaxAmount(taxAmountService.getTaxAmount());
        apiInvoice.setCreatedAt(createdAt);
        apiInvoice.setPayUntil(createdAt.plusDays(subscribe.getExpiresIn()-1+RELIEF_PERIOD));
        apiInvoice.setDeveloper(developer);
        apiInvoice.setGrossPrice();
        apiInvoice.setInvoicePayed(false);

        if(developer.getStatus() == CustomerStatus.SUSPENDED){
            developer.setStatus(CustomerStatus.ACTIVE);
            developerService.update(developer);
        }

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

    @Override
    public List<ApiInvoice> getAllInvoicesSortedByCreatedAt() {
        return apiInvoiceRepository.getAllInvoicesSortedByCreatedAt();
    }

    @Override
    public Double countAllApiInvoices() {
        return apiInvoiceRepository.countAllApiInvoices();
    }

    @Override
    public Double countPaidApiInvoices() {
        return apiInvoiceRepository.countPaidApiInvoices();
    }

    @Override
    public List<ApiInvoice> notPaidInvoicesAfterReliefPeriod(LocalDateTime now) {
        return apiInvoiceRepository.notPaidInvoicesAfterReliefPeriod(now);
    }

    @Override
    public Double sumOfApiInvoices(boolean paid) {
        return apiInvoiceRepository.sumOfApiInvoices(paid);
    }

    @Override
    public List<Double> apiInvoicesIncomeStats() {
        List<Double> result = new ArrayList<Double>();
        result.add(sumOfApiInvoices(true));
        result.add(sumOfApiInvoices(false));
        return  result;
    }
}
