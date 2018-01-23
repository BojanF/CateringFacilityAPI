package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.exceptions.NotExisting;
import com.apiApp.CateringFacilityAPI.model.api.*;
import com.apiApp.CateringFacilityAPI.model.enums.AllowSubscription;
import com.apiApp.CateringFacilityAPI.model.enums.BeverageType;
import com.apiApp.CateringFacilityAPI.model.enums.CourseType;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFacilityService {

    Facility insertFacility(String name, String username, String password, String email);

    Facility findOne(Long id);

    Facility update(Facility facility);

    void delete(Long id);

    Iterable<Facility> findAll();

    List<Course> facilityCourses(Long facilityId);

    List<Beverage> facilityBeverages(Long facilityId);

    List<FacilityLocation> facilityLocations(Long facilityId);

    List<FacilityInvoice> facilityInvoices(Long facilityId, Pageable page);

    //no junit test
    List<Facility> activeFacilities();

    AllowSubscription allowSubscription(Long facilityId);

    void suspendingFacilitiesStatusForUnpaidInvoices();

    void suspendingFacilitiesForExpiredSubscription();

    Double countInvoicesForFacilityByPaidStatus(Long facilityId, boolean status);

    List<Double> facilityInvoicesStats(Long facilityId);

    Double sumOfInvoicesForFacility(Long facilityId, boolean paid);

    Facility findFacilityByUserId(Long userId);

    //api
    List<ApiFacility> getActiveFacilities();

    ApiFacilityDetails facilityDetails(Long facilityId) throws NotExisting;

    List<ApiMenuItem> facilityCoursesByType(Long facilityId, CourseType type) throws NotExisting;

    List<ApiMenuItem> facilityBeveragesByType(Long facilityId,  BeverageType type) throws NotExisting;

    List<BeverageType> getBeveragesTypesForFacility(Long facilityId) throws NotExisting;

    List<CourseType> getCoursesTypesForFacility(Long facilityId) throws NotExisting;

    List<ApiMenuItemDetails> allCoursesForType(CourseType courseType);

    List<ApiMenuItemDetails> allBeveragesForType(BeverageType beverageType);

    ApiMenuItemDetailsTyped getDetailedMenuItem(Long menuItemId) throws NotExisting;

}
