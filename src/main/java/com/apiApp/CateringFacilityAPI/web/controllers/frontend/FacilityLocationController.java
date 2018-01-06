package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact;
import com.apiApp.CateringFacilityAPI.service.IFacilityLocationService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/fe/fac-location", produces = "application/json")
public class FacilityLocationController {

    @Autowired
    private IFacilityLocationService facilityLocationService;

    @Autowired
    private IFacilityService facilityService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityLocation insertFacilityLocation(@RequestBody FacilityLocation location){
        int x = 0;
        Facility facility = facilityService.findOne(location.getFacility().getId());
        location.setFacility(facility);
        return facilityLocationService.insertFacilityLocation(
                location.getName(),
                location.getCity(),
                location.getAddress(),
                location.getFacility()
        );
    }

    @RequestMapping(value = "/get-location/{locationId}", method = RequestMethod.GET)
    public FacilityLocation getLocationById(@PathVariable Long locationId){
        return facilityLocationService.findOne(locationId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public FacilityLocation updateLocation(@RequestBody FacilityLocation location){
        Facility facility = facilityService.findOne(location.getFacility().getId());
        location.setFacility(facility);
        return facilityLocationService.update(location);
    }

    @RequestMapping(value = "/contacts/{facilityId}", method = RequestMethod.GET)
    public List<FacilityLocationContact> facilityLocationContacts(@PathVariable Long facilityId){
        return facilityLocationService.facLocationContacts(facilityId);
    }


    @RequestMapping(value = "/delete/{facilityId}", method = RequestMethod.DELETE)
    public boolean deleteLocation(@PathVariable Long facilityId){
        facilityLocationService.delete(facilityId);
        if(facilityLocationService.findOne(facilityId) == null){
            return true;
        }
        else{
            return false;
        }
    }

}
