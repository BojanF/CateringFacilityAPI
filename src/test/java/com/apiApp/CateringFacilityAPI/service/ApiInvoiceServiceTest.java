package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
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
public class ApiInvoiceServiceTest {

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private ISubscriptionPackageService packageService;

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    @Autowired
    private ITaxAmountService taxAmountService;

    private Developer dev;
    private Developer dev2;
    private SubscriptionPackage subscriptionPackage;
    private SubscriptionPackage subscriptionPackage2;
    private TaxAmount ta;

    @Before
    public void initialize(){
        dev = developerService.insertDeveloper(
                "MarijoK",
                "black",
                "marijo@email.com",
                 CustomerStatus.ACTIVE);

        dev2 = developerService.insertDeveloper(
                "Bidik",
                "vlad",
                "bidik@email.com",
                 CustomerStatus.ACTIVE);

        subscriptionPackage = packageService.insertPackage(
                "Starter",
                10d,
                15,
                 PackageStatus.ACTIVE,
                "Ordinary 15 days package");

        subscriptionPackage2 = packageService.insertPackage(
                "Christmas special",
                5d,
                7,
                 PackageStatus.ACTIVE,
                "Christmas 2017 special offer. 50% off!!!");

        //we need at least one entry in tax table for creating invoices
        //tax attr is filled automatic with creation of invoice, in the constructor in service
        ta = taxAmountService.insertTaxAmount(18.5d);
    }

    @Test
    public void crudTestApiInvoice(){
        ApiInvoice invoice = apiInvoiceService.insertApiInvoice(
                 subscriptionPackage,
                 LocalDateTime.now(),
                 dev);

        Assert.assertNotNull(apiInvoiceService.findOne(invoice.getId()));
        //testing for total prize(with tax)
//        Double grossPrice = (1+invoice.getTaxAmount()/100d)*invoice.getSubscribe().getPrice();
        Double grossPrice = (1+invoice.getTaxAmount()/100d)*invoice.getOriginalPackagePrice();
        grossPrice = Math.round( grossPrice * 100d ) / 100d;
        Assert.assertEquals(grossPrice, invoice.getGrossPrice());

        Assert.assertEquals(false, invoice.getPayedStatus());
        Assert.assertNull(invoice.getPayedAt());

        invoice.setInvoicePayed(true);
        invoice.setPayedAt(invoice.getCreatedAt().plusDays(9l));
        invoice = apiInvoiceService.update(invoice);

        Assert.assertNotNull(invoice.getPayedAt());
        Assert.assertEquals(true, invoice.getPayedStatus());

        //new invoice for same devloper after first package is out of date
        ApiInvoice invoice2 = apiInvoiceService.insertApiInvoice(
                 subscriptionPackage,
                 invoice.getCreatedAt().plusDays(15l),
                 dev);
        //testing for total prize(with tax)
//        Double grossPrice2 = (1+invoice2.getTaxAmount()/100d)*invoice2.getSubscribe().getPrice();
        Double grossPrice2 = (1+invoice2.getTaxAmount()/100d)*invoice2.getOriginalPackagePrice();
        grossPrice2 = Math.round( grossPrice2 * 100d ) / 100d;
        Assert.assertEquals(grossPrice2, invoice2.getGrossPrice());

        Assert.assertNotNull(apiInvoiceService.findOne(invoice2.getId()));
        Assert.assertEquals(false, invoice2.getPayedStatus());
        Assert.assertNull(invoice2.getPayedAt());

        //invoice 3
        ApiInvoice invoice3 = apiInvoiceService.insertApiInvoice(
                 subscriptionPackage2,
                 LocalDateTime.now(),
                 dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(invoice3.getId()));
        Assert.assertEquals(false, invoice3.getPayedStatus());
        Assert.assertNull(invoice3.getPayedAt());
//        Double grossPrice3 = (1+invoice3.getTaxAmount()/100d)*invoice3.getSubscribe().getPrice();
        Double grossPrice3 = (1+invoice3.getTaxAmount()/100d)*invoice3.getOriginalPackagePrice();
        grossPrice3 = Math.round( grossPrice3 * 100d ) / 100d;
        Assert.assertEquals(grossPrice3, invoice3.getGrossPrice());

        //testing findAll method
        List<Long> invoiceIdentifiers = Arrays.asList(invoice.getId(), invoice2.getId(), invoice3.getId());
        Iterable<ApiInvoice> allInvoices = apiInvoiceService.findAll();
        for(ApiInvoice ai : allInvoices){
            Assert.assertEquals(true, invoiceIdentifiers.contains(ai.getId()));
        }

        apiInvoiceService.delete(invoice.getId());
        apiInvoiceService.delete(invoice2.getId());
        apiInvoiceService.delete(invoice3.getId());
        Assert.assertNull(apiInvoiceService.findOne(invoice.getId()));
        Assert.assertNull(apiInvoiceService.findOne(invoice2.getId()));
        Assert.assertNull(apiInvoiceService.findOne(invoice3.getId()));
    }

    @After
    public void delete(){
        developerService.delete(dev.getId());
        developerService.delete(dev2.getId());
        packageService.delete(subscriptionPackage.getId());
        packageService.delete(subscriptionPackage2.getId());
        taxAmountService.delete(ta.getId());
    }
}
