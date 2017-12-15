package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TaxAmountServiceTest {

    @Autowired
    private ITaxAmountService taxAmountService;

    @Test
    public void crudTestTax(){
        //create & read tests
        TaxAmount ta1 = taxAmountService.insertTaxAmount(15d);
        Assert.assertNotNull(taxAmountService.findOne(ta1.getId()));

        //update test
        Assert.assertEquals(2017, ta1.getActiveSince().getYear());
        ta1.setActiveSince(LocalDateTime.now().minusYears(1));
        ta1 = taxAmountService.update(ta1);
        Assert.assertEquals(2016, ta1.getActiveSince().getYear());

        TaxAmount ta2 = taxAmountService.insertTaxAmount(18d);
        Assert.assertNotNull(taxAmountService.findOne(ta2.getId()));

        //test getTaxAmount method
        Assert.assertEquals(18d, taxAmountService.getTaxAmount(),0);

        //delete test
        taxAmountService.delete(ta1.getId());
        taxAmountService.delete(ta2.getId());
        Assert.assertEquals(null, taxAmountService.findOne(ta1.getId()));
        Assert.assertEquals(null, taxAmountService.findOne(ta2.getId()));

    }
}
