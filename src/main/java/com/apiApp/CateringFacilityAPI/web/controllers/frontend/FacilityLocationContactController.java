package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact;
import com.apiApp.CateringFacilityAPI.model.jpa.LocationContactId;
import com.apiApp.CateringFacilityAPI.service.IFacilityLocationContactService;
import com.apiApp.CateringFacilityAPI.service.IFacilityLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value ="/fe/location-contact", produces = "application/json")
public class FacilityLocationContactController {

    @Autowired
    private IFacilityLocationContactService facilityLocationContactService;

    @Autowired
    private IFacilityLocationService facilityLocationService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityLocationContact insertLocationContact(@RequestBody FacilityLocationContact contact){
        int x =0;
        FacilityLocation location =  facilityLocationService.findOne(contact.getId().getFacilityLocationId());
        return facilityLocationContactService.insertFacilityLocationContact(contact.getId(), location);
    }

    @RequestMapping(value = "/delete/{locationId}/{telNumber}", method = RequestMethod.DELETE)
    public boolean deleteContact(@PathVariable Long locationId, @PathVariable String telNumber){
        LocationContactId id = new LocationContactId(locationId, telNumber);
        facilityLocationContactService.delete(id);
        if(facilityLocationContactService.findOne(id) == null){
            return true;
        }
        else{
            return false;
        }
    }
}
