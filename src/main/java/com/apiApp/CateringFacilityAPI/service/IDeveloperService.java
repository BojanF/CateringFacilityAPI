package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.AllowSubscription;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDeveloperService {

    Developer insertDeveloper(String username, String password, String email);

    Developer findOne(Long id);

    Developer update(Developer developer);

    void delete(Long id);

    Iterable<Developer> findAll();

    List<ApiInvoice> developerInvoices(Long devId, Pageable page);

    //no unit test
    List<Developer> activeDevelopers();

    AllowSubscription allowSubscription(Long developerId);

    void suspendingDevelopersStatusForUnpaidInvoices();

    void suspendingDevelopersForExpiredSubscription();

    Double countInvoicesForDeveloperByPaidStatus(Long developerId, boolean status);

    List<Double>developerInvoicesStats(Long developerId);

    Double sumOfInvoicesForDeveloper(Long developerId, boolean paid);
}
