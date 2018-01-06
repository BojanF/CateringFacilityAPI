package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact;

import java.util.List;

public interface IFacilityLocationService {

    FacilityLocation insertFacilityLocation(String name,
                                            String city,
                                            String address,
                                            Facility facility);

    FacilityLocation findOne(Long id);

    FacilityLocation update(FacilityLocation location);

    void delete(Long id);

    Iterable<FacilityLocation> findAll();

    List<FacilityLocationContact> facLocationContacts(Long locationId);
    
}
