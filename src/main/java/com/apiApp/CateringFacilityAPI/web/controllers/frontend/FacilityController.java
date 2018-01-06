package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "fe/facility", produces = "application/json")
public class FacilityController {

    @Autowired
    private IFacilityService facilityService;

    @RequestMapping(value = "/get-facility/{facilityId}", method = RequestMethod.GET)
    public Facility getFacilityById(@PathVariable Long facilityId){
        return facilityService.findOne(facilityId);
    }

    @RequestMapping(value = "/invoices/{facilityId}", method = RequestMethod.GET)
    public List<FacilityInvoice> facilityInvoices(@PathVariable Long facilityId){
        return facilityService.facilityInvoices(facilityId);
    }

    @RequestMapping(value = "/locations/{facilityId}", method = RequestMethod.GET)
    public List<FacilityLocation> facilityLocations(@PathVariable Long facilityId){
        return facilityService.facilityLocations(facilityId);
    }
}
