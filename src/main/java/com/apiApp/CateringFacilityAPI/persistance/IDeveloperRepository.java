package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeveloperRepository extends CrudRepository<Developer, Long> {

    @Query(value =
            "select inv " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice inv " +
            "where inv.developer.id = :devId")
    List<ApiInvoice> developerInvoices(@Param("devId")Long devId);

}
