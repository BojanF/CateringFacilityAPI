package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
