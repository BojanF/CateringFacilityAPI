package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.BeverageType;
import com.apiApp.CateringFacilityAPI.model.enums.CourseType;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FacilityServiceTest {

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IBeverageService beverageService;

    @Autowired
    private IFacilityLocationService locationService;

    @Autowired
    private IFacilityInvoiceService facilityInvoiceService;

    @Autowired
    private ISubscriptionPackageService packageService;

    @Autowired
    private ITaxAmountService taxAmountService;

    @Test
    public void crudTestFacility(){
        Facility fac1 = facilityService.insertFacility(
                "Cafe Li",
                "li",
                "passLI",
                "li@mail.c",
                 CustomerStatus.ACTIVE);
        Assert.assertNotNull(facilityService.findOne(fac1.getId()));
        Assert.assertEquals(facilityService.findOne(fac1.getId()).getId(), fac1.getId());

        Facility fac2 = facilityService.insertFacility(
                "Martini",
                "martini",
                "mariniPass",
                "martini@email.com",
                 CustomerStatus.ACTIVE);
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

    @Test
    public void allCoursesAndBeveragesForFacility(){
        Facility fac1 = facilityService.insertFacility(
                "Trend",
                "trend",
                "trendPass",
                "trend@email.com",
                CustomerStatus.ACTIVE);
        Assert.assertNotNull(facilityService.findOne(fac1.getId()));

        Facility fac2 = facilityService.insertFacility(
                "Martini",
                "martini",
                "mariniPass",
                "martini@email.com",
                CustomerStatus.ACTIVE);
        Assert.assertNotNull(facilityService.findOne(fac2.getId()));

        Course course1 = courseService.insertCourse(
                "Caesar salad",
                120d,
                false,
                fac1,
                CourseType.APPETIZER,
                null);
        Assert.assertNotNull(courseService.findOne(course1.getId()));

        Course course2 = courseService.insertCourse(
                "English breakfast",
                150d,
                false,
                fac1,
                CourseType.BREAKFAST,
                "Breakfast for champions!!! Eggs, bacon, beans, susage...");
        Assert.assertNotNull(courseService.findOne(course2.getId()));

        Course course3 = courseService.insertCourse(
                "Ambassador cake",
                350d,
                false,
                fac1,
                CourseType.DESERT,
                "Recomended for MFAs :)");
        Assert.assertNotNull(courseService.findOne(course3.getId()));

        Course course4 = courseService.insertCourse(
                "Pizza quattro stagioni",
                350d,
                true,
                fac1,
                CourseType.MAIN_COURSE,
                "Specialty of the house");
        Assert.assertNotNull(courseService.findOne(course4.getId()));

        Course course5 = courseService.insertCourse(
                "Chocolate waffle",
                130d,
                true,
                fac2,
                CourseType.MAIN_COURSE,
                "Belgian waffle with belgian chocolate");
        Assert.assertNotNull(courseService.findOne(course5.getId()));

        Course course6 = courseService.insertCourse(
                "Fruit waffle",
                130d,
                true,
                fac2,
                CourseType.MAIN_COURSE,
                "Belgian waffle with macedonian fruits");
        Assert.assertNotNull(courseService.findOne(course6.getId()));

        Beverage b1 = beverageService.insertBeverage(
                "Vodka",
                120d,
                false,
                fac2,
                BeverageType.ALCOHOL,
                "Smirnoff...");
        Assert.assertNotNull(beverageService.findOne(b1.getId()));

        Beverage b2 = beverageService.insertBeverage(
                "Whiskey",
                120d,
                false,
                fac2,
                BeverageType.ALCOHOL,
                "Johny Red");
        Assert.assertNotNull(beverageService.findOne(b2.getId()));

        //tests
        List<Course> trendCourses = facilityService.facilityCourses(fac1.getId());
        Assert.assertEquals(4, trendCourses.size());
        List<Long> trendCoursesIDs = Arrays.asList(course1.getId(), course2.getId(), course3.getId(), course4.getId());
        for(Course c : trendCourses){
            Assert.assertEquals(true, trendCoursesIDs.contains(c.getId()));
        }

        List<Course> martiniCourses = facilityService.facilityCourses(fac2.getId());
        Assert.assertEquals(2, martiniCourses.size());
        List<Long> martiniCoursesIDs = Arrays.asList(course5.getId(), course6.getId());
        for(Course c : martiniCourses){
            Assert.assertEquals(true, martiniCoursesIDs.contains(c.getId()));
        }

        List<Beverage> martiniBeverages = facilityService.facilityBeverages(fac2.getId());
        Assert.assertEquals(2, martiniBeverages.size());
        List<Long> martiniBeveragesIDs = Arrays.asList(b1.getId(), b2.getId());
        for(Beverage b : martiniBeverages){
            Assert.assertEquals(true, martiniBeveragesIDs.contains(b.getId()));
        }

        courseService.delete(course1.getId());
        courseService.delete(course2.getId());
        courseService.delete(course3.getId());
        courseService.delete(course4.getId());
        courseService.delete(course5.getId());
        courseService.delete(course6.getId());
        Assert.assertNull(courseService.findOne(course1.getId()));
        Assert.assertNull(courseService.findOne(course2.getId()));
        Assert.assertNull(courseService.findOne(course3.getId()));
        Assert.assertNull(courseService.findOne(course4.getId()));
        Assert.assertNull(courseService.findOne(course5.getId()));
        Assert.assertNull(courseService.findOne(course6.getId()));

        beverageService.delete(b1.getId());
        beverageService.delete(b2.getId());
        Assert.assertNull(beverageService.findOne(b1.getId()));
        Assert.assertNull(beverageService.findOne(b2.getId()));

        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
        Assert.assertNull(facilityService.findOne(fac1.getId()));
        Assert.assertNull(facilityService.findOne(fac2.getId()));
    }

    @Test
    public void allFacilityBeverages(){
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
        Assert.assertNotNull(facilityService.findOne(fac2.getId()));

        Beverage b1 = beverageService.insertBeverage(
                "Ladno espresso",
                60d,
                false,
                fac1,
                BeverageType.COFFEE,
                null);
        Assert.assertNotNull(beverageService.findOne(b1.getId()));

        Beverage b2 = beverageService.insertBeverage(
                "Makijato",
                80d,
                false,
                fac1,
                BeverageType.COFFEE,
                null);
        Assert.assertNotNull(beverageService.findOne(b2.getId()));

        Beverage b3 = beverageService.insertBeverage(
                "Skopksko",
                100d,
                false,
                fac1,
                BeverageType.BEER,
                null);
        Assert.assertNotNull(beverageService.findOne(b3.getId()));

        Beverage b4 = beverageService.insertBeverage(
                "Laden caj",
                70d,
                false,
                fac1,
                BeverageType.ICE_TEA,
                "Shumsko ovosje, portokal...");
        Assert.assertNotNull(beverageService.findOne(b4.getId()));

        Beverage b5 = beverageService.insertBeverage(
                "Caj",
                100d,
                false,
                fac1,
                BeverageType.TEA,
                "Franch...");

        Assert.assertNotNull(beverageService.findOne(b5.getId()));

        Beverage b6 = beverageService.insertBeverage(
                "Vodka",
                120d,
                false,
                fac2,
                BeverageType.ALCOHOL,
                "Smirnoff...");
        Assert.assertNotNull(beverageService.findOne(b6.getId()));

        Beverage b7 = beverageService.insertBeverage(
                "Whiskey",
                120d,
                false,
                fac2,
                BeverageType.ALCOHOL,
                "Johny Red");
        Assert.assertNotNull(beverageService.findOne(b7.getId()));

        List<Beverage> fac1Beverages = facilityService.facilityBeverages(fac1.getId());
        Assert.assertEquals(5, fac1Beverages.size());
        List<Long> fac1BeveragesIDs = Arrays.asList(b1.getId(), b2.getId(), b3.getId(), b4.getId(), b5.getId());
        for(Beverage b : fac1Beverages){
            Assert.assertEquals(true, fac1BeveragesIDs.contains(b.getId()));
        }

        List<Beverage> fac2Beverages = facilityService.facilityBeverages(fac2.getId());
        Assert.assertEquals(2, fac2Beverages.size());
        List<Long> fac2BeveragesIDs = Arrays.asList(b6.getId(), b7.getId());
        for(Beverage b : fac2Beverages){
            Assert.assertEquals(true, fac2BeveragesIDs.contains(b.getId()));
        }

        beverageService.delete(b1.getId());
        beverageService.delete(b2.getId());
        beverageService.delete(b3.getId());
        beverageService.delete(b4.getId());
        beverageService.delete(b5.getId());
        beverageService.delete(b6.getId());
        beverageService.delete(b7.getId());
        Assert.assertNull(beverageService.findOne(b1.getId()));
        Assert.assertNull(beverageService.findOne(b2.getId()));
        Assert.assertNull(beverageService.findOne(b3.getId()));
        Assert.assertNull(beverageService.findOne(b4.getId()));
        Assert.assertNull(beverageService.findOne(b5.getId()));
        Assert.assertNull(beverageService.findOne(b6.getId()));
        Assert.assertNull(beverageService.findOne(b7.getId()));

        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
        Assert.assertNull(facilityService.findOne(fac1.getId()));
        Assert.assertNull(facilityService.findOne(fac2.getId()));
    }

    @Test
    public void allLocationsForFacilityTest(){
        Facility fac1 = facilityService.insertFacility(
                "Restoran sidro",
                "anchor",
                "passSidro",
                "sidro@mail.com",
                CustomerStatus.ACTIVE);
        Assert.assertNotNull(facilityService.findOne(fac1.getId()));

        Facility fac2 = facilityService.insertFacility(
                "Trend",
                "trend",
                "trendPass",
                "trend@email.com",
                CustomerStatus.ACTIVE);
        Assert.assertNotNull(facilityService.findOne(fac2.getId()));

        Facility fac3 = facilityService.insertFacility(
                "McDonald`s",
                "mcd",
                "none",
                "kljucNaBrava",
                CustomerStatus.SUSPENDED);
        Assert.assertNotNull(facilityService.findOne(fac3.getId()));

        FacilityLocation sidroKarposh1 = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "ul. Bagdadska Karposh 1",
                fac1);
        Assert.assertNotNull(locationService.findOne(sidroKarposh1.getId()));

        FacilityLocation sidroKarposh4 = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "ul. Nikola Rusinski Karposh 4",
                fac1);
        Assert.assertNotNull(locationService.findOne(sidroKarposh4.getId()));

        FacilityLocation trendCityMall = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "Karposh 4 City Mall",
                fac2);
        Assert.assertNotNull(locationService.findOne(trendCityMall.getId()));

        FacilityLocation telekomTrend = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "Kej 13ti Noemvri",
                fac2);
        Assert.assertNotNull(locationService.findOne(telekomTrend.getId()));

        FacilityLocation trendGastro = locationService.insertFacilityLocation(
                "Macedonia",
                "Skopje",
                "Bul. Jane Sandanski Aerodrom",
                fac2);
        Assert.assertNotNull(locationService.findOne(trendGastro.getId()));

        List<FacilityLocation> restoranSidroLocations = facilityService.facilityLocations(fac1.getId());
        Assert.assertEquals(2, restoranSidroLocations.size());
        List<Long> restoranSidroLocationsIDs = Arrays.asList(sidroKarposh1.getId(), sidroKarposh4.getId());
        for(FacilityLocation fl : restoranSidroLocations){
            Assert.assertEquals(true, restoranSidroLocationsIDs.contains(fl.getId()));
        }

        List<FacilityLocation> trendLocations = facilityService.facilityLocations(fac2.getId());
        Assert.assertEquals(3, trendLocations.size());
        List<Long> trendLocationsIDs = Arrays.asList(trendCityMall.getId(), telekomTrend.getId(), trendGastro.getId());
        for(FacilityLocation fl : trendLocations){
            Assert.assertEquals(true, trendLocationsIDs.contains(fl.getId()));
        }

        List<FacilityLocation> mcdLocations = facilityService.facilityLocations(fac3.getId());
        Assert.assertEquals(0, mcdLocations.size());

        locationService.delete(sidroKarposh1.getId());
        locationService.delete(sidroKarposh4.getId());
        locationService.delete(trendCityMall.getId());
        locationService.delete(telekomTrend.getId());
        locationService.delete(trendGastro.getId());
        Assert.assertNull(locationService.findOne(sidroKarposh1.getId()));
        Assert.assertNull(locationService.findOne(sidroKarposh4.getId()));
        Assert.assertNull(locationService.findOne(trendCityMall.getId()));
        Assert.assertNull(locationService.findOne(telekomTrend.getId()));
        Assert.assertNull(locationService.findOne(trendGastro.getId()));

        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
        facilityService.delete(fac3.getId());
        Assert.assertNull(facilityService.findOne(fac1.getId()));
        Assert.assertNull(facilityService.findOne(fac2.getId()));
        Assert.assertNull(facilityService.findOne(fac3.getId()));
    }

    @Test
    public void facilityInvoices(){
        //we need at least one entry in tax table for creating invoices
        //tax attr is filled automatic with creation of invoice, in the constructor in service
        TaxAmount ta = taxAmountService.insertTaxAmount(18d);

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

        Facility fac3 = facilityService.insertFacility(
                "New cafe",
                "newCafe",
                "newPasss",
                "newCafe@newcafe.com",
                CustomerStatus.ACTIVE);

        SubscriptionPackage subscriptionPackage = packageService.insertPackage(
                "Starter",
                10d,
                15,
                PackageStatus.ACTIVE,
                "Ordinary 15 days package");

        FacilityInvoice invoice = facilityInvoiceService.insertFacilityInvoice(
                subscriptionPackage,

                LocalDateTime.now().minusDays(15),
                fac1);
        Assert.assertNotNull(facilityInvoiceService.findOne(invoice.getId()));

        //new invoice for same facility after first package is out of date
        FacilityInvoice invoice2 = facilityInvoiceService.insertFacilityInvoice(
                subscriptionPackage,

                LocalDateTime.now(),
                fac1);
        Assert.assertNotNull(facilityInvoiceService.findOne(invoice2.getId()));

        //invoice 3
        FacilityInvoice invoice3 = facilityInvoiceService.insertFacilityInvoice(
                subscriptionPackage,

                LocalDateTime.now(),
                fac2);
        Assert.assertNotNull(facilityInvoiceService.findOne(invoice3.getId()));

        //test scenarios for method facilityInvoices
        List<FacilityInvoice> fac1Invoices = facilityService.facilityInvoices(fac1.getId());
        Assert.assertEquals(2, fac1Invoices.size());
        List<Long> fac1InvoicesIDs = Arrays.asList(invoice.getId(), invoice2.getId());
        for(FacilityInvoice fi : fac1Invoices){
            Assert.assertEquals(true, fac1InvoicesIDs.contains(fi.getId()));
        }

        List<FacilityInvoice> fac2Invoices = facilityService.facilityInvoices(fac2.getId());
        Assert.assertEquals(1, fac2Invoices.size());
        Assert.assertEquals(invoice3.getId(), fac2Invoices.get(0).getId());

        List<FacilityInvoice> fac3Invoices = facilityService.facilityInvoices(fac3.getId());
        Assert.assertEquals(0, fac3Invoices.size());

        facilityInvoiceService.delete(invoice.getId());
        facilityInvoiceService.delete(invoice2.getId());
        facilityInvoiceService.delete(invoice3.getId());
        Assert.assertNull(facilityInvoiceService.findOne(invoice.getId()));
        Assert.assertNull(facilityInvoiceService.findOne(invoice2.getId()));
        Assert.assertNull(facilityInvoiceService.findOne(invoice3.getId()));

        packageService.delete(subscriptionPackage.getId());
        Assert.assertNull(packageService.findOne(subscriptionPackage.getId()));

        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
        facilityService.delete(fac3.getId());
        Assert.assertNull(facilityService.findOne(fac1.getId()));
        Assert.assertNull(facilityService.findOne(fac2.getId()));
        Assert.assertNull(facilityService.findOne(fac3.getId()));

        taxAmountService.delete(ta.getId());
        Assert.assertEquals(null, taxAmountService.findOne(ta.getId()));
    }
}
