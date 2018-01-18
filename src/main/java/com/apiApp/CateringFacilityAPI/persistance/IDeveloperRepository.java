package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeveloperRepository extends CrudRepository<Developer, Long> {

    @Query(value =
            "select inv\n" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice inv\n" +
            "where inv.developer.id = :devId\n" +
            "ORDER BY inv.createdAt DESC")
    List<ApiInvoice> developerInvoices(@Param("devId")Long devId, Pageable page);

    @Query(value =
            "select dev\n" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Developer dev\n" +
            "where dev.status = com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus.ACTIVE")
    List<Developer> activeDevelopers();

    @Query(value =
            "select count(invoice.id)\n " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice\n " +
            "where invoice.developer.id=:developerId and \n" +
            "invoice.invoicePayed = :status")
    Double countInvoicesForDeveloperByPaidStatus(@Param("developerId") Long developerId, @Param("status") boolean status);

    @Query(value =
            "select COALESCE(sum(fi.grossPrice),0) as sum " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice fi " +
            "where fi.developer.id = :developerId and fi.invoicePayed=:paid")
    Double sumOfInvoicesForDeveloper(@Param("developerId") Long developerId, @Param("paid") boolean paid);

}
