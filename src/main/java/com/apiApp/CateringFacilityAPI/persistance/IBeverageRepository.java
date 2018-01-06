package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.Beverage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface IBeverageRepository extends CrudRepository<Beverage, Long> {
    @Query(value = "" +
            "SELECT beverage\n" +
            "FROM com.apiApp.CateringFacilityAPI.model.jpa.Beverage beverage\n" +
            "WHERE beverage.facility.id = :facilityId")
    List<Beverage> getAllFacilityBeverages(@Param("facilityId") Long facilityId);

}
