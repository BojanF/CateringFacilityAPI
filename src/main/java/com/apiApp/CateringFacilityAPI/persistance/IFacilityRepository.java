package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.api.ApiFacility;
import com.apiApp.CateringFacilityAPI.model.api.ApiFacilityLocation;
import com.apiApp.CateringFacilityAPI.model.api.ApiMenuItem;
import com.apiApp.CateringFacilityAPI.model.api.ApiMenuItemDetails;
import com.apiApp.CateringFacilityAPI.model.enums.BeverageType;
import com.apiApp.CateringFacilityAPI.model.enums.CourseType;
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

    //api queries
    @Query(value =
            "select new com.apiApp.CateringFacilityAPI.model.api.ApiFacility(fac.id, fac.name)" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Facility fac " +
            "where fac.status = com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus.ACTIVE")
    List<ApiFacility> getActiveFacilities();

    @Query(value =
            "select new com.apiApp.CateringFacilityAPI.model.api.ApiFacilityLocation(loc.name, loc.city, loc.address)" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation loc " +
            "where loc.facility.id = :facilityId")
    List<ApiFacilityLocation> getFacilityLocations(@Param("facilityId") Long facilityId);

    @Query(value =
            "select new com.apiApp.CateringFacilityAPI.model.api.ApiMenuItem(c.id, c.name, c.price)" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Course c " +
            "where c.facility.id = :facilityId and " +
            "c.listedInMenu = true and " +
            "c.type = :courseType")
    List<ApiMenuItem> facilityCoursesByType(@Param("facilityId") Long facilityId, @Param("courseType") CourseType courseType);

    @Query(value =
            "select new com.apiApp.CateringFacilityAPI.model.api.ApiMenuItem(b.id, b.name, b.price)" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Beverage b " +
            "where b.facility.id = :facilityId and " +
            "b.listedInMenu = true and " +
            "b.type = :beverageType")
    List<ApiMenuItem> facilityBeveragesByType(@Param("facilityId") Long facilityId, @Param("beverageType") BeverageType beverageType);

    @Query(value =
            "select distinct b.type " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Beverage b " +
            "where b.facility.id = :facilityId " +
            "and b.listedInMenu = true")
    List<BeverageType> getBeveragesTypesForFacility(@Param("facilityId") Long facilityId);

    @Query(value =
            "select distinct c.type " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Course c " +
            "where c.facility.id = :facilityId " +
            "and c.listedInMenu = true")
    List<CourseType> getCoursesTypesForFacility(@Param("facilityId") Long facilityId);

    @Query(value = "select new com.apiApp.CateringFacilityAPI.model.api.ApiMenuItemDetails(c.id, c.name, c.price, c.description, c.facility.id, c.facility.name)" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Course c " +
            "where c.facility.status = com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus.ACTIVE " +
            "and c.listedInMenu = true " +
            "and c.type = :courseType " +
            "order by c.facility.id")
    List<ApiMenuItemDetails> allCoursesForType(@Param("courseType") CourseType courseType);

    @Query(value = "select new com.apiApp.CateringFacilityAPI.model.api.ApiMenuItemDetails(b.id, b.name, b.price, b.description, b.facility.id, b.facility.name)" +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Beverage b " +
            "where b.facility.status = com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus.ACTIVE " +
            "and b.listedInMenu = true " +
            "and b.type = :beverageType " +
            "order by b.facility.id")
    List<ApiMenuItemDetails> allBeveragesForType(@Param("beverageType") BeverageType beverageType);

}