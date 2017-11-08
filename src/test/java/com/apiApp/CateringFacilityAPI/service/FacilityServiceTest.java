package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import org.junit.Assert;
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
public class FacilityServiceTest {

    @Autowired
    private IFacilityService facilityService;

    @Test
    public void crudTestFacility(){
        Facility fac1 = facilityService.insertFacility(
                "Cafe Li",
                "li",
                "passLI",
                "li@mail.c",
                 CustomerStatus.ACTIVE);
        Facility fac2 = facilityService.insertFacility(
                "Martini",
                "martini",
                "mariniPass",
                "martini@email.com",
                 CustomerStatus.ACTIVE);

        Assert.assertNotNull(facilityService.findOne(fac1.getId()));
        Assert.assertEquals(facilityService.findOne(fac1.getId()).getId(), fac1.getId());
        Assert.assertNotNull(facilityService.findOne(fac2.getId()));
        Assert.assertEquals(facilityService.findOne(fac2.getId()).getId(), fac2.getId());

        Assert.assertEquals("Martini", fac2.getName());
        fac2.setName("Martini F1");
        fac2 = facilityService.update(fac2);
        Assert.assertNotEquals("Martini", fac2.getName());
        Assert.assertEquals("Martini F1", fac2.getName());

        //testing findAll method
        List<Long> facilityIdentifiers = Arrays.asList(fac1.getId(), fac2.getId());
        Iterable<Facility> facilities = facilityService.findAll();
        for (Facility f : facilities) {
            Assert.assertEquals(true, facilityIdentifiers.contains(f.getId()));
        }


        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
        Assert.assertEquals(null, facilityService.findOne(fac1.getId()));
        Assert.assertEquals(null, facilityService.findOne(fac2.getId()));

    }
}
