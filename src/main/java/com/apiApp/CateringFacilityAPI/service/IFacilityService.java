package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;

public interface IFacilityService {

    Facility insertFacility(String name, String username, String password, String email, CustomerStatus status);

    Facility findOne(Long id);

    Facility update(Facility facility);

    void delete(Long id);

    Iterable<Facility> findAll();
}
