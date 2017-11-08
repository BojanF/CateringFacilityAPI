package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
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
public class ApiInvoiceServiceTest {

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private ISubscriptionPackageService packageService;

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    private Developer dev;
    private Developer dev2;
    private SubscriptionPackage subscriptionPackage;
    private SubscriptionPackage subscriptionPackage2;

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
    public void crudTestApiInvoice(){
        ApiInvoice invoice = apiInvoiceService.insertApiInvoice(
                 subscriptionPackage,
                0.18d,
                 LocalDateTime.now(),
                 dev);

        Assert.assertNotNull(apiInvoiceService.findOne(invoice.getId()));
        Double grossPrize = (1+invoice.getTaxAmount())*invoice.getSubscribe().getPrice();
        Assert.assertEquals(grossPrize, invoice.getGrossPrice());


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
                0.18d,
                 invoice.getCreatedAt().plusDays(15l),
                 dev);
        Assert.assertNotNull(apiInvoiceService.findOne(invoice2.getId()));
        Assert.assertEquals(false, invoice2.getPayedStatus());
        Assert.assertNull(invoice2.getPayedAt());


        //invoice 3
        ApiInvoice invoice3 = apiInvoiceService.insertApiInvoice(
                 subscriptionPackage2,
                0.18d,
                 LocalDateTime.now(),
                 dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(invoice3.getId()));
        Assert.assertEquals(false, invoice3.getPayedStatus());
        Assert.assertNull(invoice3.getPayedAt());


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
    }
}
