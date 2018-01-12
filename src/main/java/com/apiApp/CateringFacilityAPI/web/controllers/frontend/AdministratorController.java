package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.service.IAdministratorService;
import com.apiApp.CateringFacilityAPI.service.ISubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/fe/admin", produces = "application/json")
public class AdministratorController {

    @Autowired
    private IAdministratorService administratorService;

    @RequestMapping(value = "/paid-invoices-stats", method = RequestMethod.GET)
    public List<Double> percentageOfPaidInvoices(){
        return administratorService.percentageStats();
    }

    @RequestMapping(value =  "/income-stats", method = RequestMethod.GET)
    List<Double> invoicesIncomeStats(){
        return  administratorService.invoicesIncomeStats();
    }


}
