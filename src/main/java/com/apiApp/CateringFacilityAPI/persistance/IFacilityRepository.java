package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacilityRepository extends CrudRepository<Facility, Long> {

    @Query(value =
            "select course\n " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Course course\n " +
            "where course.facility.id=:facilityId")
    List<Course> facilityCourses(@Param("facilityId") Long facilityId);

    @Query(value =
            "select beverage\n " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Beverage beverage\n " +
            "where beverage.facility.id=:facilityId")
    List<Beverage> facilityBeverages(@Param("facilityId") Long facilityId);

    @Query(value =
            "select location\n " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation location\n " +
            "where location.facility.id=:facilityId")
    List<FacilityLocation> facilityLocations(@Param("facilityId") Long facilityId);

    @Query(value =
            "select invoice\n " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice\n " +
            "where invoice.facility.id=:facilityId\n" +
            "order by invoice.createdAt desc")
    List<FacilityInvoice> facilityInvoices(@Param("facilityId") Long facilityId, Pageable page);

    @Query(value =
            "select fac\n" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Facility fac\n" +
            "where fac.status = com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus.ACTIVE")
    List<Facility> activeFacilities();

    @Query(value =
            "select count(invoice.id)\n " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice invoice\n " +
            "where invoice.facility.id=:facilityId and \n" +
            "invoice.invoicePayed = :status")
    Double countInvoicesForFacilityByPaidStatus(@Param("facilityId") Long facilityId, @Param("status") boolean status);

    @Query(value =
            "select COALESCE(sum(fi.grossPrice),0) as sum " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice fi " +
            "where fi.facility.id = :facilityId and fi.invoicePayed=:paid")
    Double sumOfInvoicesForFacility(@Param("facilityId") Long facilityId, @Param("paid") boolean paid);


}