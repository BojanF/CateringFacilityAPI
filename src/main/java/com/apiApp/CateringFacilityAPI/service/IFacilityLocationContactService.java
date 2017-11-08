package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact;
import com.apiApp.CateringFacilityAPI.model.jpa.LocationContactId;

public interface IFacilityLocationContactService {

    FacilityLocationContact insertFacilityLocationContact(LocationContactId id, FacilityLocation location);

    FacilityLocationContact findOne(LocationContactId id);

    void delete(LocationContactId id);

    Iterable<FacilityLocationContact> findAll();
}
