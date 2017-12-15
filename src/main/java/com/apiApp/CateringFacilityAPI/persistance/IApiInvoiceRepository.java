package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApiInvoiceRepository extends CrudRepository<ApiInvoice, Long> {

   @Query(value = "" +
           "SELECT invoice\n" +
           "FROM com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice invoice\n" +
           "ORDER BY invoice.createdAt DESC")
   List<ApiInvoice> getAllInvoicesSortedByCreatedAt();
}
