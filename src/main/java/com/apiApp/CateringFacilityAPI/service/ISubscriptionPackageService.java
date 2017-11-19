
package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;

import java.util.List;

public interface ISubscriptionPackageService {

    SubscriptionPackage insertPackage(Double price, int expiresIn, PackageStatus status, String description);

    SubscriptionPackage findOne(Long id);

    SubscriptionPackage update(SubscriptionPackage _package);

    void delete(Long id);

    Iterable<SubscriptionPackage> findAll();

    List<ApiInvoice> ApiInvoicesForPackage(Long packageId);

    List<FacilityInvoice> FacilityInvoicesForPackage(Long packageId);

    int countApiInvoicesForPackage(Long packageId);

    int countFacilityInvoicesForPackage(Long packageId);

    Double sumOfInvoicesForPackage(Long packageId, boolean paid);

}
