package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.Beverage;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.service.IBeverageService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "fe/beverage", produces="application/json")
public class BeverageController {

    @Autowired
    private IBeverageService beverageService;

    @Autowired
    private IFacilityService facilityService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Beverage insertBeverage(@RequestBody Beverage beverage){
        Facility facility = facilityService.findOne(beverage.getFacility().getId());
        int x = 0;

        return beverageService.insertBeverage(
                beverage.getName(),
                beverage.getPrice(),
                beverage.isOnPromotion(),
                facility,
                beverage.getType(),
                beverage.getDescription());
    }

    @RequestMapping(value = "/get-beverage/{beverageId}", method = RequestMethod.GET)
    public Beverage getBeverageById(@PathVariable Long beverageId){
        return beverageService.findOne(beverageId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public Beverage updateBeverage(@RequestBody Beverage beverage){
        Facility facility = facilityService.findOne(beverage.getFacility().getId());
        beverage.setFacility(facility);
        return beverageService.update(beverage);
    }

    @RequestMapping(value = "/facility-beverages/{facilityId}", method = RequestMethod.GET)
    public List<Beverage> getAllFacilityBeverages(@PathVariable Long facilityId){
       return beverageService.getAllFacilityBeverages(facilityId);
    }

    @RequestMapping(value = "/delete/{beverageId}", method = RequestMethod.DELETE)
    public boolean deleteBeverage(@PathVariable Long beverageId){
        beverageService.delete(beverageId);
        if(beverageService.findOne(beverageId) == null){
            return true;
        }
        else{
            return false;
        }

    }
}
