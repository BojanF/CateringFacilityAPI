
package com.apiApp.CateringFacilityAPI.service;

        import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
        import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;

public interface ISubscriptionPackageService {

    SubscriptionPackage insertPackage(Double price, int expiresIn, PackageStatus status, String description);

    SubscriptionPackage findOne(Long id);

    SubscriptionPackage update(SubscriptionPackage _package);

    void delete(Long id);

    Iterable<SubscriptionPackage> findAll();

}
