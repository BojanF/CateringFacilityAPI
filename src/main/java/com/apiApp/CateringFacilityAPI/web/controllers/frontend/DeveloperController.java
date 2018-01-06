package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/fe/developer", produces = "application/json")
public class DeveloperController {

    @Autowired
    private IDeveloperService developerService;

    @RequestMapping(value = "/invoices/{developerId}", method = RequestMethod.GET)
    public List<ApiInvoice> getInvoicesForDeveloper(@PathVariable Long developerId){
        return developerService.developerInvoices(developerId);
    }
}
