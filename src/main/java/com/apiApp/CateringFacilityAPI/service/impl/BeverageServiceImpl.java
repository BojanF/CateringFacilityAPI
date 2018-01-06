package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.BeverageType;
import com.apiApp.CateringFacilityAPI.model.jpa.Beverage;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.persistance.IBeverageRepository;
import com.apiApp.CateringFacilityAPI.service.IBeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BeverageServiceImpl implements IBeverageService{

    @Autowired
    private IBeverageRepository beverageRepository;

    @Override
    public Beverage insertBeverage(String name, Double price, boolean onPromotion, Facility facility, BeverageType type, String description) {
        Beverage beverage = new Beverage();
        beverage.setName(name);
        beverage.setPrice(price);
        beverage.setOnPromotion(onPromotion);
        beverage.setFacility(facility);
        beverage.setType(type);
        beverage.setDescription(description);
        return beverageRepository.save(beverage);
    }

    @Override
    public Beverage findOne(Long id) {
        return beverageRepository.findOne(id);
    }

    @Override
    public Beverage update(Beverage beverage) {
        return beverageRepository.save(beverage);
    }

    @Override
    public void delete(Long id) {
        beverageRepository.delete(id);
    }

    @Override
    public Iterable<Beverage> findAll() {
        return beverageRepository.findAll();
    }

    @Override
    public List<Beverage> getAllFacilityBeverages(Long facilityId) {
        return beverageRepository.getAllFacilityBeverages(facilityId);
    }
}
