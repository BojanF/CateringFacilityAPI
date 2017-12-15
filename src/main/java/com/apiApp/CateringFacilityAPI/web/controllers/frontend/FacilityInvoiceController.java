package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/fe/fac-invoice", produces = "application/json")
public class FacilityInvoiceController {

    @Autowired
    private IFacilityInvoiceService facilityInvoiceService;

    @RequestMapping(value="/all-sorted", method = RequestMethod.GET)
    public List<FacilityInvoice> allFacilityInvoicesSorted(){
        return facilityInvoiceService.getAllFacilityInvoicesSortedByCreatedAt();
    }
}
