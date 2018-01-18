
package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISubscriptionPackageService {

    SubscriptionPackage insertPackage(String name,
                                      Double price,
                                      int expiresIn,
                                      String description);

    SubscriptionPackage findOne(Long id);

    SubscriptionPackage update(SubscriptionPackage _package);

    void delete(Long id);

    Iterable<SubscriptionPackage> findAll();

    List<ApiInvoice> ApiInvoicesForPackage(Long packageId);

    List<FacilityInvoice> FacilityInvoicesForPackage(Long packageId);

    int countApiInvoicesForPackage(Long packageId);

    int countApiInvoicesForPackageByPaidStatus(Long packageId, boolean status);

    int countFacilityInvoicesForPackage(Long packageId);

    int countFacilityInvoicesForPackageByPaidStatus(Long packageId, boolean status);

    Double sumOfInvoicesForPackage(Long packageId, boolean paid);

    // nema unit test
    List<SubscriptionPackage> getActivePackages();

    List<Integer> packageStats(Long packageId);

    List<Integer> packagesStatusStats();

    List<Double> packageIncomeStats(Long packageId);

    List<SubscriptionPackage> packagesForMailSending();

    void sendingMailsForSubscriptionPackagesUpdates();

}
