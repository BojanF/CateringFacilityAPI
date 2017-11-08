package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.persistance.ISubscriptionPackageRepository;
import com.apiApp.CateringFacilityAPI.service.ISubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPackageServiceImpl implements ISubscriptionPackageService {

    @Autowired
    private ISubscriptionPackageRepository packageRepository;

    @Override
    public SubscriptionPackage insertPackage(Double price, int expiresIn, PackageStatus status, String description) {
        SubscriptionPackage p = new SubscriptionPackage();
        p.setPrice(price);
        p.setExpiresIn(expiresIn);
        p.setStatus(status);
        p.setDescription(description);
        return packageRepository.save(p);
    }

    @Override
    public SubscriptionPackage findOne(Long id) {
        return packageRepository.findOne(id);
    }

    @Override
    public SubscriptionPackage update(SubscriptionPackage _package) {
        return packageRepository.save(_package);
    }

    @Override
    public void delete(Long id) {
        packageRepository.delete(id);
    }

    @Override
    public Iterable<SubscriptionPackage> findAll() {
        return packageRepository.findAll();
    }
}
