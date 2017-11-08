package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.BeverageType;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Beverage;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BeverageServiceTest {

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private IBeverageService beverageService;

    private Facility fac1;

    @Before
    public void intializeFacility(){
        fac1 = facilityService.insertFacility(
                "Cafe Li",
                "li",
                "passLI",
                "li@mail.c",
                 CustomerStatus.ACTIVE);
    }

    @Test
    public void crudTestBeverage(){
        Beverage b1 = beverageService.insertBeverage(
                "Ladno espresso",
                60d,
                false,
                 fac1,
                 BeverageType.COFFEE,
                null);

        Beverage b2 = beverageService.insertBeverage(
                "Makijato",
                80d,
                false,
                 fac1,
                 BeverageType.COFFEE,
                null);

        Beverage b3 = beverageService.insertBeverage(
                "Skopksko",
                100d,
                false,
                 fac1,
                 BeverageType.BEER,
                null);

        Beverage b4 = beverageService.insertBeverage(
                "Laden caj",
                70d,
                false,
                 fac1,
                 BeverageType.ICE_TEA,
                "Shumsko ovosje, portokal...");

        Beverage b5 = beverageService.insertBeverage(
                "Caj",
                100d,
                false,
                 fac1,
                 BeverageType.TEA,
                "Franch...");

        Beverage b6 = beverageService.insertBeverage(
                "Vodka",
                120d,
                false,
                 fac1,
                 BeverageType.ALCOHOL,
                "Smirnoff...");

        Assert.assertNotNull(beverageService.findOne(b1.getId()));
        Assert.assertNotNull(beverageService.findOne(b2.getId()));
        Assert.assertNotNull(beverageService.findOne(b3.getId()));
        Assert.assertNotNull(beverageService.findOne(b4.getId()));
        Assert.assertNotNull(beverageService.findOne(b5.getId()));
        Assert.assertNotNull(beverageService.findOne(b6.getId()));

        Assert.assertNull(b2.getDescription());
        Assert.assertEquals(false, b2.isOnPromotion());
        b2.setDescription("Golemo makijato Julius...");
        b2.setOnPromotion(true);
        b2 = beverageService.update(b2);
        Assert.assertNotNull(b2.getDescription());
        Assert.assertEquals(true, b2.isOnPromotion());
        Assert.assertEquals("Golemo makijato Julius...", b2.getDescription());

        Assert.assertEquals(false, b6.isOnPromotion());
        b6.setOnPromotion(true);
        b6 = beverageService.update(b6);
        Assert.assertNotNull(b6.getDescription());
        Assert.assertEquals(true, b6.isOnPromotion());

        //testing findAll method
        List<Long> beverageIdentificators;
        beverageIdentificators = Arrays.asList(b1.getId(), b2.getId(), b3.getId(), b4.getId(), b5.getId(), b6.getId());
        Iterable<Beverage> beverages = beverageService.findAll();
        for(Beverage b : beverages){
            Assert.assertEquals(true, beverageIdentificators.contains(b.getId()));
        }

        beverageService.delete(b1.getId());
        beverageService.delete(b2.getId());
        beverageService.delete(b3.getId());
        beverageService.delete(b4.getId());
        beverageService.delete(b5.getId());
        beverageService.delete(b6.getId());

        Assert.assertNull(beverageService.findOne(b1.getId()));
        Assert.assertNull(beverageService.findOne(b2.getId()));
        Assert.assertNull(beverageService.findOne(b3.getId()));
        Assert.assertNull(beverageService.findOne(b4.getId()));
        Assert.assertNull(beverageService.findOne(b5.getId()));
        Assert.assertNull(beverageService.findOne(b6.getId()));

    }

    @After
    public void deleteFacility(){
        facilityService.delete(fac1.getId());
    }
}

