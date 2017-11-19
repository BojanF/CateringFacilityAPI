package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacilityRepository extends CrudRepository<Facility, Long> {

    @Query(value =
            "select course " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Course course " +
            "where course.facility.id=:facilityId")
    List<Course> facilityCourses(@Param("facilityId") Long facilityId);

    @Query(value =
            "select beverage " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Beverage beverage " +
            "where beverage.facility.id=:facilityId")
    List<Beverage> facilityBeverages(@Param("facilityId") Long facilityId);

    @Query(value =
            "select location " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation location " +
            "where location.facility.id=:facilityId")
    List<FacilityLocation> facilityLocations(@Param("facilityId") Long facilityId);

    @Query(value =
            "select invoice " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice " +
            "where invoice.facility.id=:facilityId")
    List<FacilityInvoice> facilityInvoices(@Param("facilityId") Long facilityId);


}