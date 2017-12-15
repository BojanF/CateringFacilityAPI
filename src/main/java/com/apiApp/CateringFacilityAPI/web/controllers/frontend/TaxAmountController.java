package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount;
import com.apiApp.CateringFacilityAPI.service.ITaxAmountService;
import org.hibernate.validator.internal.util.IgnoreJava6Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/fe/tax", produces = "application/json")
public class TaxAmountController {

    @Autowired
    private ITaxAmountService taxAmountService;

    @RequestMapping(value = "/new-tax", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TaxAmount insertNewTax(@RequestBody TaxAmount taxAmount){
        int x = 1;
        return taxAmountService.insertTaxAmount(taxAmount.getAmount());
    }

    @RequestMapping(value = "/all-taxes-sorted", method = RequestMethod.GET)
    public List<TaxAmount> allTaxesSorted(){
        return taxAmountService.allTaxesSorted();
    }

    @RequestMapping(value = "/delete-tax/{taxId}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable Long taxId){
        taxAmountService.delete(taxId);
    }
}
