package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.Administrator;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsersCreation {

    @Autowired
    private IAdministratorService administratorService;

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private IFacilityService facilityService;

    @Test
    public void createUsers(){

        Administrator a1 = administratorService.insertAdmin(
                "Vojche",
                "Stojanoski",
                "VojcheS",
                "peco",
                "vojche@mail.com");
        Assert.assertNotNull(administratorService.findOne(a1.getId()));
        Assert.assertEquals(administratorService.findOne(a1.getId()).getId(), a1.getId());
        Assert.assertNotNull(a1.getUser().getEmail());

        Facility fac1 = facilityService.insertFacility(
                "Cafe Lee",
                "CafeLee",
                "kole",
                "boko1994@gmail.com");
        Assert.assertNotNull(facilityService.findOne(fac1.getId()));
        Assert.assertEquals(facilityService.findOne(fac1.getId()).getId(), fac1.getId());
        Assert.assertNotNull(fac1.getUser().getEmail());

        Facility fac2 = facilityService.insertFacility(
                "Trend",
                "Trend",
                "passTrend",
                "ivanaf200@gmail.com");
        Assert.assertNotNull(facilityService.findOne(fac2.getId()));
        Assert.assertEquals(facilityService.findOne(fac2.getId()).getId(), fac2.getId());
        Assert.assertNotNull(fac2.getUser().getEmail());

        Facility fac3 = facilityService.insertFacility(
                "7ca",
                "Sedmica",
                "sedum",
                "sedum@gmail.com");
        Assert.assertNotNull(facilityService.findOne(fac3.getId()));
        Assert.assertEquals(facilityService.findOne(fac3.getId()).getId(), fac3.getId());
        Assert.assertNotNull(fac3.getUser().getEmail());

        Developer dev = developerService.insertDeveloper(
                "MarijoK",
                "marijo",
                "savicafilipovska@gmail.com");
        Assert.assertNotNull(developerService.findOne(dev.getId()));
        Assert.assertEquals(developerService.findOne(dev.getId()).getId(), dev.getId());
        Assert.assertNotNull(dev.getUser().getEmail());

        Developer dev2 = developerService.insertDeveloper(
                "Bidik",
                "bidikov",
                "bidik@gmail.com");
        Assert.assertNotNull(developerService.findOne(dev2.getId()));
        Assert.assertEquals(developerService.findOne(dev2.getId()).getId(), dev2.getId());
        Assert.assertNotNull(dev2.getUser().getEmail());

    }

}
