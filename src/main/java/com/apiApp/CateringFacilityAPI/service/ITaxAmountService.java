package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount;

import java.util.List;

public interface ITaxAmountService {

    TaxAmount insertTaxAmount(Double amount);

    TaxAmount findOne(Long id);

    TaxAmount update(TaxAmount taxAmount);

    void delete(Long id);

    Iterable<TaxAmount> findAll();

    Double getTaxAmount();

    List<TaxAmount> allTaxesSorted();
}
