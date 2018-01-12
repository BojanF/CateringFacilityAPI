package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.Administrator;

import java.util.List;

public interface IAdministratorService {

    Administrator insertAdmin(String name, String surname, String username, String password, String email);

    Administrator findOne(Long id);

    Administrator update(Administrator admin);

    void delete(Long id);

    Iterable<Administrator> findAll();

    //no unit test
    List<Double> percentageStats();

    List<Double>invoicesIncomeStats();


}
