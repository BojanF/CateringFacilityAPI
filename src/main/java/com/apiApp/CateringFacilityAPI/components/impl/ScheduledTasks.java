package com.apiApp.CateringFacilityAPI.components.impl;

import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import com.apiApp.CateringFacilityAPI.service.ISubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private ISubscriptionPackageService packageService;

    @Scheduled(cron = "0 15 */24 * * *")
    public void suspendingDevelopersStatusForUnpaidInvoices() {
        System.out.println("Changing developer status for unpaid invoices");//
        developerService.suspendingDevelopersStatusForUnpaidInvoices();
    }

    @Scheduled(cron = "0 40 */24 * * *")
    public void suspendingFacilitiesStatusForUnpaidInvoices() {
        System.out.println("Changing facility status for unpaid invoices");
        facilityService.suspendingFacilitiesStatusForUnpaidInvoices();
    }

    @Scheduled(cron = "0 15 1 * * *")
    public void suspendingDevelopersForExpiredSubscription(){
        System.out.println("Suspending developers for expired subscription");
        developerService.suspendingDevelopersForExpiredSubscription();
    }

    @Scheduled(cron = "0 40 1 * * *")
    public void suspendingFacilitiesForExpiredSubscription(){
        System.out.println("Suspending facilities for expired subscription");
        facilityService.suspendingFacilitiesForExpiredSubscription();
    }

    @Scheduled(cron = "0 0 */8 * * *")
    public void sendingMailsForSubscriptionPackagesUpdates(){
        System.out.println("Sending mails");
        packageService.sendingMailsForSubscriptionPackagesUpdates();
    }
}
