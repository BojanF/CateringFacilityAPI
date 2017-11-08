package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
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
public class SubscriptionPackageServiceTest {

    @Autowired
    private ISubscriptionPackageService packageService;

    @Test
    public void crudTestPackage(){

        SubscriptionPackage p = packageService.insertPackage(
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
}
