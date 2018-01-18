package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubscriptionPackageRepository extends CrudRepository<SubscriptionPackage, Long> {

    @Query(value =
            "select invoice " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice " +
            "where invoice.subscribe.id = :packageId")
    List<ApiInvoice> ApiInvoicesForPackage(@Param("packageId")Long packageId);

    @Query(value =
            "select invoice " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice " +
            "where invoice.subscribe.id = :packageId")
    List<FacilityInvoice> FacilityInvoicesForPackage(@Param("packageId")Long packageId);

    @Query(value =
            "select count(invoice.id) " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice " +
            "where invoice.subscribe.id = :packageId")
    int countApiInvoicesForPackage(@Param("packageId")Long packageId);

    @Query(value =
            "select count(invoice.id) " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice " +
            "where invoice.subscribe.id = :packageId and " +
            "invoice.invoicePayed = :status")
    int countApiInvoicesForPackageByPaidStatus(@Param("packageId")Long packageId, @Param("status")boolean status);

    @Query(value =
            "select count(invoice.id) " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice " +
            "where invoice.subscribe.id = :packageId")
    int countFacilityInvoicesForPackage(@Param("packageId")Long packageId);

    @Query(value =
            "select count(invoice.id) " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice " +
            "where invoice.subscribe.id = :packageId and " +
            "invoice.invoicePayed = :status")
    int countFacilityInvoicesForPackageByPaidStatus(@Param("packageId")Long packageId, @Param("status")boolean status);

    @Query(value =
            "select COALESCE(sum(ai.grossPrice),0) as sum " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice ai " +
            "where ai.subscribe.id=:packageId and ai.invoicePayed=:paid")
    Double sumOfApiInvoicesForPackage(@Param("packageId")Long packageId, @Param("paid") boolean paid);

    @Query(value =
            "select COALESCE(sum(fi.grossPrice),0) as sum " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice fi " +
            "where fi.subscribe.id=:packageId and fi.invoicePayed=:paid")
    Double sumOfFacilityInvoicesForPackage(@Param("packageId")Long packageId, @Param("paid") boolean paid);

    @Query(value =
            "select package " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage package " +
            "where package.status = com.apiApp.CateringFacilityAPI.model.enums.PackageStatus.ACTIVE")
    List<SubscriptionPackage> getActivePackages();

    @Query(value =
            "select count(package.id) " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage package " +
            "where package.status = :status")
    int countPackagesWithStatus(@Param("status") PackageStatus status);

    @Query(value =
            "select package " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage package " +
            "where package.sendMail = true")
    List<SubscriptionPackage> packagesForMailSending();


}
