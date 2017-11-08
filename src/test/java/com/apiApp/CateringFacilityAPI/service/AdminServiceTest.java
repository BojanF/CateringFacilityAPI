package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.jpa.Administrator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AdminServiceTest {

    @Autowired
    private IAdministratorService administratorService;

    @Test
    public void crudTestAdmin(){

        Administrator a1 = administratorService.insertAdmin(
                "Bojan",
                "Filipovski",
                "BojanF",
                "pw1",
                "bf@mail.com");
        Assert.assertNotNull(administratorService.findOne(a1.getId()));
        Assert.assertEquals(administratorService.findOne(a1.getId()).getId(), a1.getId());

        Assert.assertEquals("BojanF", a1.getUsername());
        a1.setUsername("BojanFF");
        a1 = administratorService.update(a1);
        Assert.assertNotEquals("BojanF", a1.getUsername());
        Assert.assertEquals("BojanFF", a1.getUsername());

        //testing findAll method
        Iterable<Administrator> admins = administratorService.findAll();
        int count = 0;
        for (Administrator a : admins) {
            count++;
        }
        Assert.assertEquals(1, count);

        administratorService.delete(a1.getId());
        Assert.assertEquals(null, administratorService.findOne(a1.getId()));

    }
}
