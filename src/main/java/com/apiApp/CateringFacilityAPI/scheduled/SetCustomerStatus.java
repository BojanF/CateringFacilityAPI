package com.apiApp.CateringFacilityAPI.scheduled;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SetCustomerStatus {

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private IFacilityInvoiceService facilityInvoiceService;

    @Autowired
    private IFacilityService facilityService;

//    @Scheduled(fixedRate = 5000, initialDelay = 1000)
//    @Scheduled(cron = "0 5 */1 * * *")
    @Scheduled(cron = "0 15 2 * * *")
    public void updateDeveloperStatusForUnpaidInvoices() {

        System.out.println("Developer status for unpaid invoices");
        List<ApiInvoice> invoices = apiInvoiceService.notPaidInvoicesAfterReliefPeriod(LocalDateTime.now());

        for(ApiInvoice invoice : invoices){
            invoice.getDeveloper().setStatus(CustomerStatus.SUSPENDED);
            developerService.update(invoice.getDeveloper());
        }
    }

    @Scheduled(cron = "0 35 2 * * *")
    public void updateFacilityStatusForUnpaidInvoices() {

        System.out.println("Facility status for unpaid invoices");
        List<FacilityInvoice> invoices = facilityInvoiceService.notPaidInvoicesAfterReliefPeriod(LocalDateTime.now());

        for(FacilityInvoice invoice : invoices){
            invoice.getFacility().setStatus(CustomerStatus.SUSPENDED);
            facilityService.update(invoice.getFacility());
        }
    }
}
