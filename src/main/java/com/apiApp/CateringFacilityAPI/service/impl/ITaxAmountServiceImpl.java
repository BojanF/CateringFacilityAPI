package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount;
import com.apiApp.CateringFacilityAPI.persistance.ITaxAmountRepository;
import com.apiApp.CateringFacilityAPI.service.ITaxAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ITaxAmountServiceImpl implements ITaxAmountService {

    @Autowired
    private ITaxAmountRepository taxAmountRepository;

    @Override
    public TaxAmount insertTaxAmount(Double amount) {
        TaxAmount ta = new TaxAmount();
        ta.setActiveSince(LocalDateTime.now());
        ta.setAmount(amount);
        return taxAmountRepository.save(ta);
    }

    @Override
    public TaxAmount findOne(Long id) {
        return taxAmountRepository.findOne(id);
    }

    @Override
    public TaxAmount update(TaxAmount taxAmount) {
        return taxAmountRepository.save(taxAmount);
    }

    @Override
    public void delete(Long id) {
        taxAmountRepository.delete(id);
    }

    @Override
    public Iterable<TaxAmount> findAll() {
        return taxAmountRepository.findAll();
    }

    @Override
    public Double getTaxAmount() {
        return taxAmountRepository.getTaxAmount();
    }

    @Override
    public List<TaxAmount> allTaxesSorted() {
        return taxAmountRepository.allTaxesSorted();
    }
}
