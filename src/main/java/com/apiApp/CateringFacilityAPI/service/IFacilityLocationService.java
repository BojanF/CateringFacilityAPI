package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;

public interface IFacilityLocationService {

    FacilityLocation insertFacilityLocation(String country, String city, String address, Facility facility);

    FacilityLocation findOne(Long id);

    FacilityLocation update(FacilityLocation location);

    void delete(Long id);

    Iterable<FacilityLocation> findAll();
    
}
