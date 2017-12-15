package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacilityInvoiceRepository extends CrudRepository<FacilityInvoice, Long> {

    @Query(value = "" +
            "SELECT invoice\n" +
            "FROM com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice\n" +
            "ORDER BY invoice.createdAt DESC")
    List<FacilityInvoice> getAllFacilityInvoicesSortedByCreatedAt();

}
