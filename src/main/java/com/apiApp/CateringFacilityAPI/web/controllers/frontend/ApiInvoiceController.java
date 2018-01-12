package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/fe/api-invoice", produces="application/json")
public class ApiInvoiceController {

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    @Autowired
    private IDeveloperService developerService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiInvoice insertApiInvoice(@RequestBody ApiInvoice invoice){
        Developer developer = developerService.findOne(invoice.getDeveloper().getId());
        return apiInvoiceService.insertApiInvoice(invoice.getSubscribe(),
                                                  developer);
    }

    @RequestMapping(value = "/all-sorted", method = RequestMethod.GET)
    List<ApiInvoice> allApiInvoiceSorted(){
       return apiInvoiceService.getAllInvoicesSortedByCreatedAt();
    }
}
