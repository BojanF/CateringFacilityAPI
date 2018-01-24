package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/fe/fac-invoice", produces = "application/json")
public class FacilityInvoiceController {

    @Autowired
    private IFacilityInvoiceService facilityInvoiceService;

    @Autowired
    private IFacilityService facilityService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityInvoice insertApiInvoice(@RequestBody FacilityInvoice invoice){
        Facility facility = facilityService.findOne(invoice.getFacility().getId());
        return facilityInvoiceService.insertFacilityInvoice(invoice.getSubscribe(),
                facility);
    }

    @RequestMapping(value="/all-sorted", method = RequestMethod.GET)
    public List<FacilityInvoice> allFacilityInvoicesSorted(){
        return facilityInvoiceService.getAllFacilityInvoicesSortedByCreatedAt();
    }

    @RequestMapping(value = "/update/{invoiceId}", method = RequestMethod.PATCH)
    public FacilityInvoice updateBeverage(@PathVariable Long invoiceId){
        FacilityInvoice facilityInvoice = facilityInvoiceService.findOne(invoiceId);
        facilityInvoice.setPayedAt(LocalDateTime.now());
        facilityInvoice.setInvoicePayed(true);
        return facilityInvoiceService.update(facilityInvoice);
    }

    @RequestMapping(value = "/get-by-id/{invoiceId}", method = RequestMethod.GET)
    public FacilityInvoice getBeverageById(@PathVariable Long invoiceId){
        return facilityInvoiceService.findOne(invoiceId);
    }
}
