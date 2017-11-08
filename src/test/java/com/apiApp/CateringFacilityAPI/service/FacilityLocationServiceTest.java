package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
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
public class FacilityLocationServiceTest {

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private IFacilityLocationService locationService;

    private Facility fac1;
    private Facility fac2;

    @Before
    public void initializeFacilities(){
        fac1 = facilityService.insertFacility(
                "Cafe Li",
                "li",
                "passLI",
                "li@mail.c",
                 CustomerStatus.ACTIVE);
        fac2 = facilityService.insertFacility(
                "Trend",
                "trend",
                "trendPass",
                "trend@email.com",
                 CustomerStatus.ACTIVE);
    }

    @Test
    public void crudTestFacilityLocation(){

        FacilityLocation cafeLi = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "Mlecen na kosh",
                 fac1);

        FacilityLocation trendCityMall = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "Karposh 4 City Mall",
                 fac2);

        FacilityLocation telekomTrend = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "Kej 13ti Noemvri",
                 fac2);

        FacilityLocation trendGastro = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "Bul. Jane Sandanski Aerodrom",
                 fac2);

        Assert.assertNotNull(locationService.findOne(cafeLi.getId()));
        Assert.assertNotNull(locationService.findOne(trendCityMall.getId()));
        Assert.assertNotNull(locationService.findOne(telekomTrend.getId()));
        Assert.assertNotNull(locationService.findOne(trendGastro.getId()));

        //testing findAll method
        List<Long> locationIdentificators = Arrays.asList(cafeLi.getId(), trendCityMall.getId(), telekomTrend.getId(), trendGastro.getId());
        Iterable<FacilityLocation> locations = locationService.findAll();
        for(FacilityLocation fl : locations){
            Assert.assertEquals(true, locationIdentificators.contains(fl.getId()));
        }

        locationService.delete(cafeLi.getId());
        locationService.delete(trendCityMall.getId());
        locationService.delete(telekomTrend.getId());
        locationService.delete(trendGastro.getId());

        Assert.assertNull(locationService.findOne(cafeLi.getId()));
        Assert.assertNull(locationService.findOne(trendCityMall.getId()));
        Assert.assertNull(locationService.findOne(telekomTrend.getId()));
        Assert.assertNull(locationService.findOne(trendGastro.getId()));

    }

    @After
    public void deleteFacilities(){
        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
    }
}
