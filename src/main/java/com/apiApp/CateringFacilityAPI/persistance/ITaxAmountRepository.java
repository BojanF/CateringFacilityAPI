package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaxAmountRepository extends CrudRepository<TaxAmount, Long>{

    @Query(value =
            "SELECT t.amount\n" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount t\n" +
            "where t.activeSince = (\n" +
            "select max(t.activeSince)\n" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount t)")
    Double getTaxAmount();

    @Query(value = "" +
            "SELECT tax\n" +
            "FROM com.apiApp.CateringFacilityAPI.model.jpa.TaxAmount tax\n" +
            "ORDER BY tax.activeSince DESC")
    List<TaxAmount> allTaxesSorted();
}
