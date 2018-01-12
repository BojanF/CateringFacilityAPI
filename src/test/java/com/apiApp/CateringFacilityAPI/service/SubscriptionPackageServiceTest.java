package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class SubscriptionPackageServiceTest {

    @Autowired
    private ISubscriptionPackageService packageService;

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private IFacilityInvoiceService facilityInvoiceService;

    @Autowired
    private ITaxAmountService taxAmountService;

    private Developer dev;
    private Developer dev2;
    private Facility fac1;
    private Facility fac2;
    private TaxAmount ta;

    @Before
    public void initialize(){
        fac1 = facilityService.insertFacility(
                "Cafe Li",
                "li",
                "passLI",
                "li@mail.c",
                CustomerStatus.ACTIVE);
        fac2 = facilityService.insertFacility(
                "Martini",
                "martini",
                "mariniPass",
                "martini@email.com",
                CustomerStatus.ACTIVE);

        dev = developerService.insertDeveloper(
                "VojcheS",
                "peco",
                "v@mail.com",
                CustomerStatus.ACTIVE);
        dev2 = developerService.insertDeveloper(
                "MarijoK",
                "black",
                "marijo@email.com",
                CustomerStatus.ACTIVE);

        //we need at least one entry in tax table for creating invoices
        //tax attr is filled automatic with creation of invoice, in the constructor in service
        ta = taxAmountService.insertTaxAmount(18d);
    }

    @Test
    public void crudTestPackage(){

        SubscriptionPackage p = packageService.insertPackage(
                "Christmas special",
                5d,
                7,
                 PackageStatus.ACTIVE,
                "Christmas 2017 special offer. 50% off!!!");
        Assert.assertNotNull(packageService.findOne(p.getId()));
        Assert.assertEquals(packageService.findOne(p.getId()).getId(), p.getId());

        Assert.assertEquals(p.getStatus(), PackageStatus.ACTIVE);
        p.setStatus(PackageStatus.SUSPENDED);
        p = packageService.update(p);
        Assert.assertNotEquals(p.getStatus(), PackageStatus.ACTIVE);
        Assert.assertEquals(p.getStatus(), PackageStatus.SUSPENDED);

        SubscriptionPackage p2 = packageService.insertPackage(
                "Starter",
                10d,
                15,
                 PackageStatus.ACTIVE,
                "Ordinary 15 days package");
        Assert.assertNotNull(packageService.findOne(p2.getId()));
        Assert.assertEquals(packageService.findOne(p2.getId()).getId(), p2.getId());

        //testing findAll method
        List<Long> packageIdentifiers = Arrays.asList(p.getId(), p2.getId());
        Iterable<SubscriptionPackage> packages = packageService.findAll();

        for (SubscriptionPackage pcg : packages) {
            Assert.assertEquals(true, packageIdentifiers.contains(pcg.getId()));
        }


        packageService.delete(p.getId());
        packageService.delete(p2.getId());
        Assert.assertEquals(null, packageService.findOne(p.getId()));
        Assert.assertEquals(null, packageService.findOne(p2.getId()));

    }

    @Test
    public void invoicesForPackage(){
        SubscriptionPackage packageOne = packageService.insertPackage(
                "Starter",
                10d,
                15,
                PackageStatus.ACTIVE,
                "Ordinary 15 days package");
        Assert.assertNotNull(packageService.findOne(packageOne.getId()));

        SubscriptionPackage packageTwo = packageService.insertPackage(
                "Christmas special",
                5d,
                7,
                PackageStatus.ACTIVE,
                "Christmas 2017 special offer. 50% off!!!");
        Assert.assertNotNull(packageService.findOne(packageTwo.getId()));

        ApiInvoice devInvoice1 = apiInvoiceService.insertApiInvoice(
                packageOne,

//                LocalDateTime.now().minusDays(14),
                dev);
        Assert.assertNotNull(apiInvoiceService.findOne(devInvoice1.getId()));
        devInvoice1.setCreatedAt(devInvoice1.getCreatedAt().minusDays(15));
        devInvoice1.setPayUntil(devInvoice1.getPayUntil().minusDays(15));
        devInvoice1 = apiInvoiceService.update(devInvoice1);

        ApiInvoice devInvoice2 = apiInvoiceService.insertApiInvoice(
                packageTwo,

//                LocalDateTime.now(),
                dev);
        Assert.assertNotNull(apiInvoiceService.findOne(devInvoice2.getId()));

        ApiInvoice dev2Invoice1 = apiInvoiceService.insertApiInvoice(
                packageOne,

//                LocalDateTime.now().minusDays(30),
                dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(dev2Invoice1.getId()));
        dev2Invoice1.setCreatedAt(dev2Invoice1.getCreatedAt().minusDays(30));
        dev2Invoice1.setPayUntil(dev2Invoice1.getPayUntil().minusDays(30));
        dev2Invoice1 = apiInvoiceService.update(dev2Invoice1);

        dev2Invoice1.setInvoicePayed(true);
        dev2Invoice1.setPayedAt(dev2Invoice1.getCreatedAt().plusDays(9l));
        dev2Invoice1 = apiInvoiceService.update(dev2Invoice1);
        Assert.assertNotNull(dev2Invoice1.getPayedAt());
        Assert.assertEquals(true, dev2Invoice1.getPayedStatus());

        ApiInvoice dev2Invoice2 = apiInvoiceService.insertApiInvoice(
                packageOne,

//                LocalDateTime.now().minusDays(15),
                dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(dev2Invoice2.getId()));
        dev2Invoice2.setCreatedAt(dev2Invoice2.getCreatedAt().minusDays(15));
        dev2Invoice2.setPayUntil(dev2Invoice2.getPayUntil().minusDays(15));
        dev2Invoice2 = apiInvoiceService.update(dev2Invoice2);

        dev2Invoice2.setInvoicePayed(true);
        dev2Invoice2.setPayedAt(dev2Invoice2.getCreatedAt().plusDays(9l));
        dev2Invoice2 = apiInvoiceService.update(dev2Invoice2);
        Assert.assertNotNull(dev2Invoice2.getPayedAt());
        Assert.assertEquals(true, dev2Invoice2.getPayedStatus());

        ApiInvoice dev2Invoice3 = apiInvoiceService.insertApiInvoice(
                packageTwo,

//                LocalDateTime.now(),
                dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(dev2Invoice3.getId()));

        FacilityInvoice fac1Invoice1 = facilityInvoiceService.insertFacilityInvoice(
                packageOne,

//                LocalDateTime.now().minusDays(15),
                fac1);
        Assert.assertNotNull(facilityInvoiceService.findOne(fac1Invoice1.getId()));
        fac1Invoice1.setCreatedAt(fac1Invoice1.getCreatedAt().minusDays(15));
        fac1Invoice1.setPayUntil(fac1Invoice1.getPayUntil().minusDays(15));
        fac1Invoice1 = facilityInvoiceService.update(fac1Invoice1);

        FacilityInvoice fac1Invoice2 = facilityInvoiceService.insertFacilityInvoice(
                packageOne,

//                LocalDateTime.now(),
                fac1);
        Assert.assertNotNull(facilityInvoiceService.findOne(fac1Invoice2.getId()));

        FacilityInvoice fac2Invoice1 = facilityInvoiceService.insertFacilityInvoice(
                packageTwo,

//                LocalDateTime.now(),
                fac2);
        Assert.assertNotNull(facilityInvoiceService.findOne(fac2Invoice1.getId()));
        fac2Invoice1.setInvoicePayed(true);
        fac2Invoice1.setPayedAt(fac2Invoice1.getCreatedAt().plusDays(3l));
        fac2Invoice1 = facilityInvoiceService.update(fac2Invoice1);
        Assert.assertNotNull(fac2Invoice1.getPayedAt());
        Assert.assertEquals(true, fac2Invoice1.getPayedStatus());

        //tests
        int countPackage1ApiInv = packageService.countApiInvoicesForPackage(packageOne.getId());
        int countPackage1FacInv = packageService.countFacilityInvoicesForPackage(packageOne.getId());
        int countPackage2ApiInv = packageService.countApiInvoicesForPackage(packageTwo.getId());
        int countPackage2FacInv = packageService.countFacilityInvoicesForPackage(packageTwo.getId());
        Assert.assertEquals(5, countPackage1ApiInv+countPackage1FacInv);
        Assert.assertEquals(3, countPackage2ApiInv+countPackage2FacInv);

        List<ApiInvoice> apiInvoicesForPackage1 = packageService.ApiInvoicesForPackage(packageOne.getId());
        Assert.assertEquals(3, apiInvoicesForPackage1.size());
        List<Long> apiInvoicesForPackage1IDs = Arrays.asList(devInvoice1.getId(), dev2Invoice1.getId(), dev2Invoice2.getId());
        for(ApiInvoice ai : apiInvoicesForPackage1){
            Assert.assertEquals(true, apiInvoicesForPackage1IDs.contains(ai.getId()));
        }

        List<ApiInvoice> apiInvoicesForPackage2 = packageService.ApiInvoicesForPackage(packageTwo.getId());
        Assert.assertEquals(2, apiInvoicesForPackage2.size());
        List<Long> apiInvoicesForPackage2IDs = Arrays.asList(devInvoice2.getId(), dev2Invoice3.getId());
        for(ApiInvoice ai : apiInvoicesForPackage2){
            Assert.assertEquals(true, apiInvoicesForPackage2IDs.contains(ai.getId()));
        }

        List<FacilityInvoice> facilityInvoicesForPackage1 = packageService.FacilityInvoicesForPackage(packageOne.getId());
        Assert.assertEquals(2, facilityInvoicesForPackage1.size());
        List<Long> facilityInvoicesForPackage1IDs = Arrays.asList(fac1Invoice1.getId(), fac1Invoice2.getId());
        for(FacilityInvoice fi : facilityInvoicesForPackage1){
            Assert.assertEquals(true, facilityInvoicesForPackage1IDs.contains(fi.getId()));
        }

        List<FacilityInvoice> facilityInvoicesForPackage2 = packageService.FacilityInvoicesForPackage(packageTwo.getId());
        Assert.assertEquals(1, facilityInvoicesForPackage2.size());
        Assert.assertEquals(fac2Invoice1.getId(), facilityInvoicesForPackage2.get(0).getId());


        apiInvoiceService.delete(devInvoice1.getId());
        apiInvoiceService.delete(devInvoice2.getId());
        apiInvoiceService.delete(dev2Invoice1.getId());
        apiInvoiceService.delete(dev2Invoice2.getId());
        apiInvoiceService.delete(dev2Invoice3.getId());
        Assert.assertNull(apiInvoiceService.findOne(devInvoice1.getId()));
        Assert.assertNull(apiInvoiceService.findOne(devInvoice2.getId()));
        Assert.assertNull(apiInvoiceService.findOne(dev2Invoice1.getId()));
        Assert.assertNull(apiInvoiceService.findOne(dev2Invoice2.getId()));
        Assert.assertNull(apiInvoiceService.findOne(dev2Invoice3.getId()));

        facilityInvoiceService.delete(fac1Invoice1.getId());
        facilityInvoiceService.delete(fac1Invoice2.getId());
        facilityInvoiceService.delete(fac2Invoice1.getId());
        Assert.assertNull(facilityInvoiceService.findOne(fac1Invoice1.getId()));
        Assert.assertNull(facilityInvoiceService.findOne(fac1Invoice2.getId()));
        Assert.assertNull(facilityInvoiceService.findOne(fac2Invoice1.getId()));

        packageService.delete(packageOne.getId());
        packageService.delete(packageTwo.getId());
        Assert.assertNull(packageService.findOne(packageOne.getId()));
        Assert.assertNull(packageService.findOne(packageTwo.getId()));


    }

    @Test
    public void sumOfPaidUnpaidInvoicesForPackage(){
        SubscriptionPackage packageOne = packageService.insertPackage(
                "Starter",
                10d,
                15,
                PackageStatus.ACTIVE,
                "Ordinary 15 days package");
        Assert.assertNotNull(packageService.findOne(packageOne.getId()));

        SubscriptionPackage packageTwo = packageService.insertPackage(
                "Christmas special",
                5d,
                7,
                PackageStatus.ACTIVE,
                "Christmas 2017 special offer. 50% off!!!");
        Assert.assertNotNull(packageService.findOne(packageTwo.getId()));

        ApiInvoice devInvoice1 = apiInvoiceService.insertApiInvoice(
                packageOne,

//                LocalDateTime.now().minusDays(14),
                dev);
        Assert.assertNotNull(apiInvoiceService.findOne(devInvoice1.getId()));
        devInvoice1.setCreatedAt(devInvoice1.getCreatedAt().minusDays(15));
        devInvoice1.setPayUntil(devInvoice1.getPayUntil().minusDays(15));
        devInvoice1 = apiInvoiceService.update(devInvoice1);

        ApiInvoice devInvoice2 = apiInvoiceService.insertApiInvoice(
                packageTwo,

//                LocalDateTime.now(),
                dev);
        Assert.assertNotNull(apiInvoiceService.findOne(devInvoice2.getId()));

        ApiInvoice dev2Invoice1 = apiInvoiceService.insertApiInvoice(
                packageOne,

//                LocalDateTime.now().minusDays(30),
                dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(dev2Invoice1.getId()));
        dev2Invoice1.setCreatedAt(dev2Invoice1.getCreatedAt().minusDays(30));
        dev2Invoice1.setPayUntil(dev2Invoice1.getPayUntil().minusDays(30));
        dev2Invoice1 = apiInvoiceService.update(dev2Invoice1);

        dev2Invoice1.setInvoicePayed(true);
        dev2Invoice1.setPayedAt(dev2Invoice1.getCreatedAt().plusDays(9l));
        dev2Invoice1 = apiInvoiceService.update(dev2Invoice1);
        Assert.assertNotNull(dev2Invoice1.getPayedAt());
        Assert.assertEquals(true, dev2Invoice1.getPayedStatus());

        ApiInvoice dev2Invoice2 = apiInvoiceService.insertApiInvoice(
                packageOne,

//                LocalDateTime.now().minusDays(15),
                dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(dev2Invoice2.getId()));
        dev2Invoice2.setCreatedAt(dev2Invoice2.getCreatedAt().minusDays(15));
        dev2Invoice2.setPayUntil(dev2Invoice2.getPayUntil().minusDays(15));
        dev2Invoice2 = apiInvoiceService.update(dev2Invoice2);

        dev2Invoice2.setInvoicePayed(true);
        dev2Invoice2.setPayedAt(dev2Invoice2.getCreatedAt().plusDays(9l));
        dev2Invoice2 = apiInvoiceService.update(dev2Invoice2);
        Assert.assertNotNull(dev2Invoice2.getPayedAt());
        Assert.assertEquals(true, dev2Invoice2.getPayedStatus());

        ApiInvoice dev2Invoice3 = apiInvoiceService.insertApiInvoice(
                packageTwo,

//                LocalDateTime.now(),
                dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(dev2Invoice3.getId()));

        FacilityInvoice fac1Invoice1 = facilityInvoiceService.insertFacilityInvoice(
                packageOne,

//                LocalDateTime.now().minusDays(15),
                fac1);
        Assert.assertNotNull(facilityInvoiceService.findOne(fac1Invoice1.getId()));
        fac1Invoice1.setCreatedAt(fac1Invoice1.getCreatedAt().minusDays(15));
        fac1Invoice1.setPayUntil(fac1Invoice1.getPayUntil().minusDays(15));
        fac1Invoice1 = facilityInvoiceService.update(fac1Invoice1);

        FacilityInvoice fac1Invoice2 = facilityInvoiceService.insertFacilityInvoice(
                packageOne,

//                LocalDateTime.now(),
                fac1);
        Assert.assertNotNull(facilityInvoiceService.findOne(fac1Invoice2.getId()));

        FacilityInvoice fac2Invoice1 = facilityInvoiceService.insertFacilityInvoice(
                packageTwo,

//                LocalDateTime.now(),
                fac2);

        //sum of paid invoices for packageOne
        Double paidPackageOneInvoices = packageService.sumOfInvoicesForPackage(packageOne.getId(), true);
        Assert.assertEquals(devInvoice1.getGrossPrice()*2, paidPackageOneInvoices, 0);
        //sum of unpaid invoices for packageOne
        Double unpaidPackageOneInvoices = packageService.sumOfInvoicesForPackage(packageOne.getId(), false);
        Assert.assertEquals(devInvoice1.getGrossPrice()*3, unpaidPackageOneInvoices, 0);

        //sum of paid invoices for packageTwo
        Double paidPackageTwoInvoices = packageService.sumOfInvoicesForPackage(packageTwo.getId(), true);
        Assert.assertEquals(0, paidPackageTwoInvoices, 0);

        //sum of unpaid invoices for packageTwo
        Double unpaidPackageTwoInvoices = packageService.sumOfInvoicesForPackage(packageTwo.getId(), false);
        Assert.assertEquals(fac2Invoice1.getGrossPrice()*3, unpaidPackageTwoInvoices, 0);

        apiInvoiceService.delete(devInvoice1.getId());
        apiInvoiceService.delete(devInvoice2.getId());
        apiInvoiceService.delete(dev2Invoice1.getId());
        apiInvoiceService.delete(dev2Invoice2.getId());
        apiInvoiceService.delete(dev2Invoice3.getId());
        Assert.assertNull(apiInvoiceService.findOne(devInvoice1.getId()));
        Assert.assertNull(apiInvoiceService.findOne(devInvoice2.getId()));
        Assert.assertNull(apiInvoiceService.findOne(dev2Invoice1.getId()));
        Assert.assertNull(apiInvoiceService.findOne(dev2Invoice2.getId()));
        Assert.assertNull(apiInvoiceService.findOne(dev2Invoice3.getId()));

        facilityInvoiceService.delete(fac1Invoice1.getId());
        facilityInvoiceService.delete(fac1Invoice2.getId());
        facilityInvoiceService.delete(fac2Invoice1.getId());
        Assert.assertNull(facilityInvoiceService.findOne(fac1Invoice1.getId()));
        Assert.assertNull(facilityInvoiceService.findOne(fac1Invoice2.getId()));
        Assert.assertNull(facilityInvoiceService.findOne(fac2Invoice1.getId()));

        packageService.delete(packageOne.getId());
        packageService.delete(packageTwo.getId());
        Assert.assertNull(packageService.findOne(packageOne.getId()));
        Assert.assertNull(packageService.findOne(packageTwo.getId()));
    }

    @After
    public void delete(){
        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
        developerService.delete(dev.getId());
        developerService.delete(dev2.getId());
        taxAmountService.delete(ta.getId());
    }
}
