package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.*;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityRepository;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements IFacilityService {

    @Autowired
    private IFacilityRepository facilityRepository;

    @Override
    public Facility insertFacility(String name, String username, String password, String email, CustomerStatus status) {
        Facility facility = new Facility();
        facility.setName(name);
        facility.setUsername(username);
        facility.setPassword(password);
        facility.setEmail(email);
        facility.setStatus(CustomerStatus.ACTIVE);
        facility.setUsedTrial(false);
        return facilityRepository.save(facility);
    }

    @Override
    public Facility findOne(Long id) {
        return facilityRepository.findOne(id);
    }

    @Override
    public Facility update(Facility facility) {
        return facilityRepository.save(facility);
    }

    @Override
    public void delete(Long id) {
        facilityRepository.delete(id);
    }

    @Override
    public Iterable<Facility> findAll() {
        return facilityRepository.findAll();
    }

    @Override
    public List<Course> facilityCourses(Long facilityId) {
        return facilityRepository.facilityCourses(facilityId);
    }

    @Override
    public List<Beverage> facilityBeverages(Long facilityId) {
        return facilityRepository.facilityBeverages(facilityId);
    }

    @Override
    public List<FacilityLocation> facilityLocations(Long facilityId){
        return facilityRepository.facilityLocations(facilityId);
    }

    @Override
    public List<FacilityInvoice> facilityInvoices(Long facilityId){
        return facilityRepository.facilityInvoices(facilityId);
    }
}
