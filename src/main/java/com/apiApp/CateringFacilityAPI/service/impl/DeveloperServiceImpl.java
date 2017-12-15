package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.persistance.IDeveloperRepository;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperServiceImpl implements IDeveloperService {

    @Autowired
    private IDeveloperRepository developerRepository;

    @Override
    public Developer insertDeveloper(String username, String password, String email, CustomerStatus status) {
        Developer developer = new Developer();
        developer.setUsername(username);
        developer.setPassword(password);
        developer.setEmail(email);
        developer.setStatus(status);
        developer.setUsedTrial(false);
        developer.setRole(Role.ROLE_DEVELOPER);
        return developerRepository.save(developer);
    }

    @Override
    public Developer findOne(Long id) {
        return developerRepository.findOne(id);
    }

    @Override
    public Developer update(Developer developer) {
        return developerRepository.save(developer);
    }

    @Override
    public void delete(Long id) {
        developerRepository.delete(id);
    }

    @Override
    public Iterable<Developer> findAll() {
        return developerRepository.findAll();
    }

    @Override
    public List<ApiInvoice> developerInvoices(Long devId) {
        return developerRepository.developerInvoices(devId);
    }
}
