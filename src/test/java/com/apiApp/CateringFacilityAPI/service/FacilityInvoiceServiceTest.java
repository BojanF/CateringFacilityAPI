package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount;
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

    @Autowired
    private ITaxAmountService taxAmountService;

    private Facility fac1;
    private Facility fac2;
    private SubscriptionPackage subscriptionPackage;
    private SubscriptionPackage subscriptionPackage2;
    private TaxAmount ta;
    private TaxAmount ta2;

    @Before
    public void initialize(){
        fac1 = facilityService.insertFacility(
                "Cafe Li",
                "li",
                "passLI",
                "li@mail.c");
        fac2 = facilityService.insertFacility(
                "Martini",
                "martini",
                "mariniPass",
                "martini@email.com");

        subscriptionPackage = packageService.insertPackage(
                "Starter",
                10d,
                15,
                "Ordinary 15 days package");

        subscriptionPackage2 = packageService.insertPackage(
                "Christmas special",
                5d,
                7,
                "Christmas 2017 special offer. 50% off!!!");

        //we need at least one entry in tax table for creating invoices
        //tax attr is filled automatic with creation of invoice, in the constructor in service
        ta = taxAmountService.insertTaxAmount(25d);
    }

    @Test
    public void crudTestFacilityInvoice(){
        Assert.assertEquals(CustomerStatus.SUSPENDED, fac1.getStatus());
        FacilityInvoice invoice = facilityInvoiceService.insertFacilityInvoice(
                subscriptionPackage,
//                LocalDateTime.now().minusDays(15),
                fac1);
        Assert.assertNotNull(facilityInvoiceService.findOne(invoice.getId()));
        Assert.assertEquals(CustomerStatus.ACTIVE, invoice.getFacility().getStatus());
        invoice.setCreatedAt(invoice.getCreatedAt().minusDays(15));
        invoice.setPayUntil(invoice.getPayUntil().minusDays(15));
        invoice = facilityInvoiceService.update(invoice);

//        Double grossPrice = (1+invoice.getTaxAmount()/100d)*invoice.getSubscribe().getPrice();
        Double grossPrice = (1+invoice.getTaxAmount()/100d)*invoice.getOriginalPackagePrice();
        grossPrice = Math.round(grossPrice * 100d) / 100d;
        Assert.assertEquals(grossPrice, invoice.getGrossPrice());

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
//                LocalDateTime.now(),
                fac1);

        Assert.assertNotNull(facilityInvoiceService.findOne(invoice2.getId()));
        Assert.assertEquals(false, invoice2.getPayedStatus());
        Assert.assertNull(invoice2.getPayedAt());
//        Double grossPrice2 = (1+invoice2.getTaxAmount()/100d)*invoice2.getSubscribe().getPrice();
        Double grossPrice2 = (1+invoice2.getTaxAmount()/100d)*invoice2.getOriginalPackagePrice();
        grossPrice2 = Math.round(grossPrice2 * 100d) / 100d;
        Assert.assertEquals(grossPrice2, invoice2.getGrossPrice());

        //invoice 3
        //test invoice free of tax - 0%
        ta2 = taxAmountService.insertTaxAmount(0d);
        FacilityInvoice invoice3 = facilityInvoiceService.insertFacilityInvoice(
                subscriptionPackage2,
//                LocalDateTime.now(),
                fac2);

        Assert.assertNotNull(facilityInvoiceService.findOne(invoice3.getId()));
        Assert.assertEquals(false, invoice3.getPayedStatus());
        Assert.assertNull(invoice3.getPayedAt());
//        Double grossPrice3 = (1+invoice3.getTaxAmount()/100d)*invoice3.getSubscribe().getPrice();
        Double grossPrice3 = (1+invoice3.getTaxAmount()/100d)*invoice3.getOriginalPackagePrice();
        grossPrice3 = Math.round(grossPrice3 * 100d) / 100d;
        Assert.assertEquals(grossPrice3, invoice3.getGrossPrice());

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
        taxAmountService.delete(ta.getId());
        taxAmountService.delete(ta2.getId());
    }
}
