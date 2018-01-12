package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IApiInvoiceRepository extends CrudRepository<ApiInvoice, Long> {

   @Query(value = "" +
           "SELECT invoice\n" +
           "FROM com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice\n" +
           "ORDER BY invoice.createdAt DESC")
   List<ApiInvoice> getAllInvoicesSortedByCreatedAt();

   @Query(value =
           "select count(invoice.id) " +
           "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice ")
   Double countAllApiInvoices();

   @Query(value =
           "select count(invoice.id) " +
           "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice " +
           "where invoice.invoicePayed = true")
   Double countPaidApiInvoices();

   @Query(value =
           "select invoice " +
           "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice " +
           "where invoice.payUntil < :now and " +
           "invoice.invoicePayed = false and " +
           "invoice.developer.status = com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus.ACTIVE")
   List<ApiInvoice> notPaidInvoicesAfterReliefPeriod(@Param("now") LocalDateTime now);

   @Query(value =
           "select COALESCE(sum(ai.grossPrice),0) as sum " +
           "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice ai " +
           "where ai.invoicePayed=:paid")
   Double sumOfApiInvoices(@Param("paid") boolean paid);

}
