package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IFacilityInvoiceRepository extends CrudRepository<FacilityInvoice, Long> {

    @Query(value = "" +
            "SELECT invoice\n" +
            "FROM com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice\n" +
            "ORDER BY invoice.createdAt DESC")
    List<FacilityInvoice> getAllFacilityInvoicesSortedByCreatedAt();

    @Query(value =
            "select count(invoice.id) " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice ")
    Double countAllFacilityInvoices();

    @Query(value =
            "select count(invoice.id) " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice " +
            "where invoice.invoicePayed = true")
    Double countPaidFacilityInvoices();

    @Query(value =
            "select invoice " +
                    "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice " +
                    "where invoice.payUntil < :now and " +
                    "invoice.invoicePayed = false and " +
                    "invoice.facility.status = com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus.ACTIVE")
    List<FacilityInvoice> notPaidInvoicesAfterReliefPeriod(@Param("now") LocalDateTime now);

    @Query(value =
            "select COALESCE(sum(fi.grossPrice),0) as sum " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice fi " +
            "where fi.invoicePayed=:paid")
    Double sumOfFacilityInvoices(@Param("paid") boolean paid);

}
