package com.apiApp.CateringFacilityAPI.service;

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

    @Autowired
    private  IFacilityLocationContactService contactService;

    private Facility fac1;
    private Facility fac2;

    @Before
    public void initializeFacilities(){
        fac1 = facilityService.insertFacility(
                "Cafe Li",
                "li",
                "passLI",
                "li@mail.c");
        fac2 = facilityService.insertFacility(
                "Trend",
                "trend",
                "trendPass",
                "trend@email.com");
    }

    @Test
    public void crudTestFacilityLocation(){

        FacilityLocation cafeLi = locationService.insertFacilityLocation(
                "Cafe Li Mlecen",
                "Skopje",
                "Mlecen na kosh",
                 fac1);
        Assert.assertNotNull(locationService.findOne(cafeLi.getId()));

        FacilityLocation trendCityMall = locationService.insertFacilityLocation(
                "City Mall",
                "Skopje",
                "Karposh 4 City Mall",
                 fac2);
        Assert.assertNotNull(locationService.findOne(trendCityMall.getId()));

        FacilityLocation telekomTrend = locationService.insertFacilityLocation(
                "TElekom Trend",
                "Skopje",
                "Kej 13ti Noemvri",
                 fac2);
        Assert.assertNotNull(locationService.findOne(telekomTrend.getId()));

        FacilityLocation trendGastro = locationService.insertFacilityLocation(
                "Trend Gastro",
                "Skopje",
                "Bul. Jane Sandanski Aerodrom",
                 fac2);
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

    @Test
    public void facLocationContacts(){
        FacilityLocation cafeLi = locationService.insertFacilityLocation(
                "Cafe Li Mlecen",
                "Skopje",
                "Mlecen na kosh",
                fac1);
        Assert.assertNotNull(locationService.findOne(cafeLi.getId()));

        LocationContactId id1 = new LocationContactId(fac1.getId(), "023030330");
        FacilityLocationContact contact1 = contactService.insertFacilityLocationContact(id1, cafeLi);
        Assert.assertNotNull(contactService.findOne(contact1.getId()));

        LocationContactId id2 = new LocationContactId(fac1.getId(), "070012789");
        FacilityLocationContact contact2 = contactService.insertFacilityLocationContact(id2, cafeLi);
        Assert.assertNotNull(contactService.findOne(contact2.getId()));

        List<FacilityLocationContact> cafeLiContacts = locationService.facLocationContacts(cafeLi.getId());
        Assert.assertEquals(2, cafeLiContacts.size());
        List<LocationContactId> cafeLiContactsIDs = Arrays.asList(id1, id2);
        for (FacilityLocationContact c : cafeLiContacts){
            Assert.assertEquals(true, cafeLiContactsIDs.contains(c.getId()));
        }

        FacilityLocation trendCityMall = locationService.insertFacilityLocation(
                "City Mall",
                "Skopje",
                "Karposh 4 City Mall",
                fac2);
        Assert.assertNotNull(locationService.findOne(trendCityMall.getId()));

        LocationContactId id3 = new LocationContactId(fac2.getId(), "023034330");
        FacilityLocationContact contact3 = contactService.insertFacilityLocationContact(id3, trendCityMall);
        Assert.assertNotNull(contactService.findOne(contact3.getId()));

        LocationContactId id4 = new LocationContactId(fac2.getId(), "070017783");
        FacilityLocationContact contact4 = contactService.insertFacilityLocationContact(id4, trendCityMall);
        Assert.assertNotNull(contactService.findOne(contact4.getId()));

        LocationContactId id5 = new LocationContactId(fac2.getId(), "223305");
        FacilityLocationContact contact5 = contactService.insertFacilityLocationContact(id5, trendCityMall);
        Assert.assertNotNull(contactService.findOne(contact5.getId()));

        List<FacilityLocationContact> trendCityMallContacts = locationService.facLocationContacts(trendCityMall.getId());
        Assert.assertEquals(3, trendCityMallContacts.size());
        List<LocationContactId> trendCityMallContactsIDs = Arrays.asList(id3, id4, id5);
        for (FacilityLocationContact c : trendCityMallContacts){
            Assert.assertEquals(true, trendCityMallContactsIDs.contains(c.getId()));
        }


        contactService.delete(id1);
        contactService.delete(id2);
        contactService.delete(id3);
        contactService.delete(id4);
        contactService.delete(id5);
        Assert.assertNull(contactService.findOne(id1));
        Assert.assertNull(contactService.findOne(id2));
        Assert.assertNull(contactService.findOne(id3));
        Assert.assertNull(contactService.findOne(id4));
        Assert.assertNull(contactService.findOne(id5));

        locationService.delete(cafeLi.getId());
        locationService.delete(trendCityMall.getId());
        Assert.assertNull(locationService.findOne(cafeLi.getId()));
        Assert.assertNull(locationService.findOne(trendCityMall.getId()));
    }

    @After
    public void deleteFacilities(){
        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
    }
}
