package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
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
public class FacilityInvoiceServiceTest {

    @Autowired
    private IFacilityInvoiceService facilityInvoiceService;

    @Autowired
    private ISubscriptionPackageService packageService;

    @Autowired
    private IFacilityService facilityService;

    private Facility fac1;
    private Facility fac2;
    private SubscriptionPackage subscriptionPackage;
    private SubscriptionPackage subscriptionPackage2;

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

        subscriptionPackage = packageService.insertPackage(
                10d,
                15,
                 PackageStatus.ACTIVE,
                "Ordinary 15 days package");

        subscriptionPackage2 = packageService.insertPackage(
                5d,
                7,
                 PackageStatus.ACTIVE,
                "Christmas 2017 special offer. 50% off!!!");
    }

    @Test
    public void crudTestFacilityInvoice(){
        FacilityInvoice invoice = facilityInvoiceService.insertFacilityInvoice(
                subscriptionPackage,
                0.18d,
                LocalDateTime.now().minusDays(15),
                fac1);

        Assert.assertNotNull(facilityInvoiceService.findOne(invoice.getId()));
        Double grossPrize = (1+invoice.getTaxAmount())*invoice.getSubscribe().getPrice();
        Assert.assertEquals(grossPrize, invoice.getGrossPrice());


        Assert.assertEquals(false, invoice.getPayedStatus());
        Assert.assertNull(invoice.getPayedAt());

        invoice.setInvoicePayed(true);
        invoice.setPayedAt(invoice.getCreatedAt().plusDays(9l));
        invoice = facilityInvoiceService.update(invoice);

        Assert.assertNotNull(invoice.getPayedAt());
        Assert.assertEquals(true, invoice.getPayedStatus());

        //new invoice for same facility after first package is out of date
        FacilityInvoice invoice2 = facilityInvoiceService.insertFacilityInvoice(
                subscriptionPackage,
                0.18d,
                LocalDateTime.now(),
                fac1);
        Assert.assertNotNull(facilityInvoiceService.findOne(invoice2.getId()));
        Assert.assertEquals(false, invoice2.getPayedStatus());
        Assert.assertNull(invoice2.getPayedAt());


        //invoice 3
        FacilityInvoice invoice3 = facilityInvoiceService.insertFacilityInvoice(
                subscriptionPackage2,
                0.18d,
                LocalDateTime.now(),
                fac2);
        Assert.assertNotNull(facilityInvoiceService.findOne(invoice3.getId()));
        Assert.assertEquals(false, invoice3.getPayedStatus());
        Assert.assertNull(invoice3.getPayedAt());


        //testing findAll method
        List<Long> invoiceIdentifiers = Arrays.asList(invoice.getId(), invoice2.getId(), invoice3.getId());
        Iterable<FacilityInvoice> allInvoices = facilityInvoiceService.findAll();
        for(FacilityInvoice ai : allInvoices){
            Assert.assertEquals(true, invoiceIdentifiers.contains(ai.getId()));
        }

        facilityInvoiceService.delete(invoice.getId());
        facilityInvoiceService.delete(invoice2.getId());
        facilityInvoiceService.delete(invoice3.getId());
        Assert.assertNull(facilityInvoiceService.findOne(invoice.getId()));
        Assert.assertNull(facilityInvoiceService.findOne(invoice2.getId()));
        Assert.assertNull(facilityInvoiceService.findOne(invoice3.getId()));
    }

    @After
    public void delete(){
        facilityService.delete(fac1.getId());
        facilityService.delete(fac2.getId());
        packageService.delete(subscriptionPackage.getId());
        packageService.delete(subscriptionPackage2.getId());
    }
}
