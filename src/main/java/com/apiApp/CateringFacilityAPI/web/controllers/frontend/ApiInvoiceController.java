package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/fe/api-invoice", produces="application/json")
public class ApiInvoiceController {

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    @RequestMapping(value = "/all-sorted", method = RequestMethod.GET)
    List<ApiInvoice> allApiInvoiceSorted(){
       return apiInvoiceService.getAllInvoicesSortedByCreatedAt();
    }
}
