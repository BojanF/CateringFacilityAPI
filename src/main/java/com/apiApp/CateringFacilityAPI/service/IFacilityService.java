package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.AllowSubscription;
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

}
