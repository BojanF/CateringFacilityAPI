package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
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
public class DeveloperServiceTest {

    @Autowired
    private IDeveloperService developerService;

    @Test
    public void crudTestDeveloper(){

        Developer dev = developerService.insertDeveloper(
                "VojcheS",
                "peco",
                "v@mail.com",
                 CustomerStatus.ACTIVE);
        Assert.assertNotNull(developerService.findOne(dev.getId()));
        Assert.assertEquals(developerService.findOne(dev.getId()).getId(), dev.getId());

        Assert.assertEquals("peco", dev.getPassword());
        Assert.assertEquals("v@mail.com", dev.getEmail());
        dev.setPassword("pass");
        dev.setEmail("vojche@gmail.com");
        dev = developerService.update(dev);
        Assert.assertNotEquals("peco", dev.getPassword());
        Assert.assertNotEquals("v@mail.com", dev.getEmail());
        Assert.assertEquals("pass", dev.getPassword());
        Assert.assertEquals("vojche@gmail.com", dev.getEmail());

        Developer dev2 = developerService.insertDeveloper(
                "MarijoK",
                "black",
                "marijo@email.com",
                 CustomerStatus.ACTIVE);
        Developer dev3 = developerService.insertDeveloper(
                "Bidik",
                "vlad",
                "bidik@email.com",
                 CustomerStatus.ACTIVE);
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

}
