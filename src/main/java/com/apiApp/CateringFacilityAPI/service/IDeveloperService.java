package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;

public interface IDeveloperService {

    Developer insertDeveloper(String username, String password, String email, CustomerStatus status);

    Developer findOne(Long id);

    Developer update(Developer developer);

    void delete(Long id);

    Iterable<Developer> findAll();
}
