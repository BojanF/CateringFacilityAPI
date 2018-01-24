package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @RequestMapping(value = "/update/{invoiceId}", method = RequestMethod.PATCH)
    public ApiInvoice updateBeverage(@PathVariable Long invoiceId){
        ApiInvoice apiInvoice = apiInvoiceService.findOne(invoiceId);
        apiInvoice.setPayedAt(LocalDateTime.now());
        apiInvoice.setInvoicePayed(true);
        return apiInvoiceService.update(apiInvoice);
    }

    @RequestMapping(value = "/all-sorted", method = RequestMethod.GET)
    List<ApiInvoice> allApiInvoiceSorted(){
       return apiInvoiceService.getAllInvoicesSortedByCreatedAt();
    }

    @RequestMapping(value = "/get-by-id/{invoiceId}", method = RequestMethod.GET)
    public ApiInvoice getBeverageById(@PathVariable Long invoiceId){
        return apiInvoiceService.findOne(invoiceId);
    }
}
