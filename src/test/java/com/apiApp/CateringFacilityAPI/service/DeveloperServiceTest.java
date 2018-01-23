package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount;
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
public class DeveloperServiceTest {

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    @Autowired
    private ISubscriptionPackageService packageService;

    @Autowired
    private ITaxAmountService taxAmountService;

    @Test
    public void crudTestDeveloper(){

        Developer dev = developerService.insertDeveloper(
                "VojcheS",
                "peco",
                "v@mail.com");
        Assert.assertNotNull(developerService.findOne(dev.getId()));
        Assert.assertEquals(developerService.findOne(dev.getId()).getId(), dev.getId());

//        Assert.assertEquals("peco", dev.getPassword());
//        Assert.assertEquals("v@mail.com", dev.getEmail());
//        dev.setPassword("pass");
//        dev.setEmail("vojche@gmail.com");
//        dev = developerService.update(dev);
//        Assert.assertNotEquals("peco", dev.getPassword());
//        Assert.assertNotEquals("v@mail.com", dev.getEmail());
//        Assert.assertEquals("pass", dev.getPassword());
//        Assert.assertEquals("vojche@gmail.com", dev.getEmail());

        Developer dev2 = developerService.insertDeveloper(
                "MarijoK",
                "black",
                "marijo@email.com");
        Developer dev3 = developerService.insertDeveloper(
                "Bidik",
                "vlad",
                "bidik@email.com");
        Assert.assertNotNull(developerService.findOne(dev2.getId()));
        Assert.assertNotNull(developerService.findOne(dev3.getId()));

        //testing findAll method
        List<Long> developerIdentifiers = Arrays.asList(dev.getId(), dev2.getId(), dev3.getId());
        Iterable<Developer> developers = developerService.findAll();
        for (Developer d : developers) {
            Assert.assertEquals(true, developerIdentifiers.contains(d.getId()));
        }


        developerService.delete(dev.getId());
        developerService.delete(dev2.getId());
        developerService.delete(dev3.getId());
        Assert.assertEquals(null, developerService.findOne(dev.getId()));
        Assert.assertEquals(null, developerService.findOne(dev2.getId()));
        Assert.assertEquals(null, developerService.findOne(dev3.getId()));


    }

    @Test
    public void developerInvoicesTest(){
        //we need at least one entry in tax table for creating invoices
        //tax attr is filled automatic with creation of invoice, in the constructor in service
        TaxAmount ta = taxAmountService.insertTaxAmount(18d);

        Developer dev = developerService.insertDeveloper(
                "VojcheS",
                "peco",
                "v@mail.com");
        Assert.assertNotNull(developerService.findOne(dev.getId()));

        Developer dev2 = developerService.insertDeveloper(
                "MarijoK",
                "black",
                "marijo@email.com");
        Assert.assertNotNull(developerService.findOne(dev2.getId()));

        SubscriptionPackage subscriptionPackage = packageService.insertPackage(
                "Starter",
                10d,
                15,
                "Ordinary 15 days package");
        Assert.assertNotNull(packageService.findOne(subscriptionPackage.getId()));

        ApiInvoice devInvoice1 = apiInvoiceService.insertApiInvoice(
                subscriptionPackage,

//                LocalDateTime.now().minusDays(14),
                dev);
        Assert.assertNotNull(apiInvoiceService.findOne(devInvoice1.getId()));
        devInvoice1.setCreatedAt(devInvoice1.getCreatedAt().minusDays(15));
        devInvoice1.setPayUntil(devInvoice1.getPayUntil().minusDays(15));
        devInvoice1 = apiInvoiceService.update(devInvoice1);

        ApiInvoice devInvoice2 = apiInvoiceService.insertApiInvoice(
                subscriptionPackage,

//                LocalDateTime.now(),
                dev);
        Assert.assertNotNull(apiInvoiceService.findOne(devInvoice2.getId()));

        //test scenarios for developerInvoices method
        List<ApiInvoice> devInvoices = developerService.developerInvoices(dev.getId(), null);
        Assert.assertEquals(2, devInvoices.size());
        List<Long> devInvoicesIDs = Arrays.asList(devInvoice1.getId(), devInvoice2.getId());
        for(ApiInvoice ai : devInvoices){
            Assert.assertEquals(true, devInvoicesIDs.contains(ai.getId()));
        }

        ApiInvoice dev2Invoice1 = apiInvoiceService.insertApiInvoice(
                subscriptionPackage,

//                LocalDateTime.now().minusDays(30),
                dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(dev2Invoice1.getId()));
        dev2Invoice1.setCreatedAt(devInvoice1.getCreatedAt().minusDays(30));
        dev2Invoice1.setPayUntil(dev2Invoice1.getPayUntil().minusDays(30));
        dev2Invoice1 = apiInvoiceService.update(dev2Invoice1);

        dev2Invoice1.setInvoicePayed(true);
        dev2Invoice1.setPayedAt(dev2Invoice1.getCreatedAt().plusDays(9l));
        dev2Invoice1 = apiInvoiceService.update(dev2Invoice1);
        Assert.assertNotNull(dev2Invoice1.getPayedAt());
        Assert.assertEquals(true, dev2Invoice1.getPayedStatus());

        ApiInvoice dev2Invoice2 = apiInvoiceService.insertApiInvoice(
                subscriptionPackage,

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
                subscriptionPackage,

//                LocalDateTime.now(),
                dev2);
        Assert.assertNotNull(apiInvoiceService.findOne(dev2Invoice3.getId()));

        List<ApiInvoice> dev2Invoices = developerService.developerInvoices(dev2.getId(), null);
        Assert.assertEquals(3, dev2Invoices.size());
        List<Long> dev2InvoicesIDs = Arrays.asList(dev2Invoice1.getId(), dev2Invoice2.getId(), dev2Invoice3.getId());
        for(ApiInvoice ai : dev2Invoices){
            Assert.assertEquals(true, dev2InvoicesIDs.contains(ai.getId()));
        }


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

        packageService.delete(subscriptionPackage.getId());
        Assert.assertNull(packageService.findOne(subscriptionPackage.getId()));

        developerService.delete(dev.getId());
        developerService.delete(dev2.getId());
        Assert.assertEquals(null, developerService.findOne(dev.getId()));
        Assert.assertEquals(null, developerService.findOne(dev2.getId()));

        taxAmountService.delete(ta.getId());
        Assert.assertEquals(null, taxAmountService.findOne(ta.getId()));
    }
}
