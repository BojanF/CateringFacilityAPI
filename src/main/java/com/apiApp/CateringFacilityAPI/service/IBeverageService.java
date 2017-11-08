package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.BeverageType;
import com.apiApp.CateringFacilityAPI.model.jpa.Beverage;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;

public interface IBeverageService {

    Beverage insertBeverage(String name, Double price, boolean onPromotion, Facility facility, BeverageType type, String description);

    Beverage findOne(Long id);

    Beverage update(Beverage beverage);

    void delete(Long id);

    Iterable<Beverage> findAll();

}
