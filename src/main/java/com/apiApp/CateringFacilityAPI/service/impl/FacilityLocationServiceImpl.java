package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityLocationRepository;
import com.apiApp.CateringFacilityAPI.service.IFacilityLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityLocationServiceImpl implements IFacilityLocationService{

    @Autowired
    private IFacilityLocationRepository locationRepository;

    @Override
    public FacilityLocation insertFacilityLocation(String country, String city, String address, Facility facility) {

        FacilityLocation fl = new FacilityLocation();
        fl.setCountry(country);
        fl.setCity(city);
        fl.setAddress(address);
        fl.setFacility(facility);

        return locationRepository.save(fl);
    }

    @Override
    public FacilityLocation findOne(Long id) {
        return locationRepository.findOne(id);
    }

    @Override
    public FacilityLocation update(FacilityLocation location) {
        return locationRepository.save(location);
    }

    @Override
    public void delete(Long id) {
        locationRepository.delete(id);
    }

    @Override
    public Iterable<FacilityLocation> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public List<FacilityLocationContact> facLocationContacts(Long locationId) {
        return locationRepository.facLocationContacts(locationId);
    }
}
