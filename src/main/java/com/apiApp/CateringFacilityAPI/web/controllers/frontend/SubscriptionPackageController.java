package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.service.ISubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 5600)
@RestController
@RequestMapping(value = "/fe/package", produces = "application/json")
public class SubscriptionPackageController {

    @Autowired
    private ISubscriptionPackageService packageService;

    @RequestMapping(value = "/new-package", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionPackage save(@RequestBody SubscriptionPackage sb){
        return packageService.insertPackage(
                sb.getName(),
                sb.getPrice(),
                sb.getExpiresIn(),
                sb.getStatus(),
                sb.getDescription());
    }

    @RequestMapping(value="/get-package/{packageId}", method = RequestMethod.GET)
    public SubscriptionPackage getById(@PathVariable Long packageId){
        return packageService.findOne(packageId);
    }

    @RequestMapping(value = "/update-package", method = RequestMethod.PATCH)
    public SubscriptionPackage updatePackage(@RequestBody SubscriptionPackage sb){
        return packageService.update(sb);
    }

    @RequestMapping(value = "/all-packages", method = RequestMethod.GET)
    public Iterable<SubscriptionPackage> getAll(){
        return packageService.findAll();
    }

}
