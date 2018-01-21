package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacilityLocationRepository extends CrudRepository<FacilityLocation, Long> {

    @Query(value =
            "select contact " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact contact " +
            "where contact.location.id = :locationId")
    List<FacilityLocationContact> facLocationContacts(@Param("locationId") Long locationId);

    //api queries
    @Query(value =
            "select contact.id.telephoneNumber " +
                    "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact contact " +
                    "where contact.location.id = :locationId")
    List<String> facilityLocationContacts(@Param("locationId") Long locationId);

}
