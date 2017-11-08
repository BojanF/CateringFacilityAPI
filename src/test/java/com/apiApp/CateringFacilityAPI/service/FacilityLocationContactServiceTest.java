package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact;
import com.apiApp.CateringFacilityAPI.model.jpa.LocationContactId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FacilityLocationContactServiceTest {

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private IFacilityLocationService locationService;

    @Autowired
    private  IFacilityLocationContactService contactService;

    private Facility fac1;
    private FacilityLocation cafeLi;

    @Before
    public void initialize(){
        fac1 = facilityService.insertFacility(
                "Cafe Li",
                "li",
                "passLI",
                "li@mail.c",
                 CustomerStatus.ACTIVE);

        cafeLi = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "Mlecen na kosh",
                 fac1);
    }

    @Test
    public void crudTestFacilityContact(){
        LocationContactId id1 = new LocationContactId(fac1.getId(), "023030330");
        FacilityLocationContact contact1 = contactService.insertFacilityLocationContact(id1, cafeLi);

        LocationContactId id2 = new LocationContactId(fac1.getId(), "070012789");
        FacilityLocationContact contact2 = contactService.insertFacilityLocationContact(id2, cafeLi);

        Assert.assertNotNull(contactService.findOne(id1));
        Assert.assertNotNull(contactService.findOne(id2));

        contactService.delete(id1);
        contactService.delete(id2);
        Assert.assertNull(contactService.findOne(id1));
        Assert.assertNull(contactService.findOne(id2));

    }

    @After
    public void delete(){

        locationService.delete(cafeLi.getId());
        facilityService.delete(fac1.getId());

    }
}
