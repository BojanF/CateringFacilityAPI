package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.exceptions.NotExisting;
import com.apiApp.CateringFacilityAPI.model.api.*;
import com.apiApp.CateringFacilityAPI.model.enums.*;
import com.apiApp.CateringFacilityAPI.model.jpa.*;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityRepository;
import com.apiApp.CateringFacilityAPI.service.IBeverageService;
import com.apiApp.CateringFacilityAPI.service.ICourseService;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityServiceImpl implements IFacilityService {

    @Autowired
    private IFacilityRepository facilityRepository;

    @Autowired
    private IFacilityInvoiceService facilityInvoiceService;

    @Autowired
    private IBeverageService beverageService;

    @Autowired
    private ICourseService courseService;

    @Override
    public Facility insertFacility(String name, String username, String password, String email) {
        Facility facility = new Facility();
        facility.setName(name);
//        facility.setUsername(username);
//        facility.setPassword(password);
//        facility.setEmail(email);
        facility.setStatus(CustomerStatus.SUSPENDED);
        facility.setUsedTrial(false);
//        facility.setRole(Role.FACILITY);
        return facilityRepository.save(facility);
    }

    @Override
    public Facility findOne(Long id) {
        return facilityRepository.findOne(id);
    }

    @Override
    public Facility update(Facility facility) {
        return facilityRepository.save(facility);
    }

    @Override
    public void delete(Long id) {
        facilityRepository.delete(id);
    }

    @Override
    public Iterable<Facility> findAll() {
        return facilityRepository.findAll();
    }

    @Override
    public List<Course> facilityCourses(Long facilityId) {
        return facilityRepository.facilityCourses(facilityId);
    }

    @Override
    public List<Beverage> facilityBeverages(Long facilityId) {
        return facilityRepository.facilityBeverages(facilityId);
    }

    @Override
    public List<FacilityLocation> facilityLocations(Long facilityId){
        return facilityRepository.facilityLocations(facilityId);
    }

    @Override
    public List<FacilityInvoice> facilityInvoices(Long facilityId, Pageable page){
        return facilityRepository.facilityInvoices(facilityId, page);
    }

    @Override
    public List<Facility> activeFacilities(){
        return facilityRepository.activeFacilities();
    }

    @Override
    public AllowSubscription allowSubscription(Long facilityId) {

        List<FacilityInvoice> lastTwo = facilityInvoices(facilityId, new PageRequest(0, 2));
        if(lastTwo.size() == 0){
            return AllowSubscription.YES;
        }
        else {
            FacilityInvoice last = lastTwo.get(0);
            LocalDateTime subscriptionEnds = last.getCreatedAt().plusDays(last.getSubscribe().getExpiresIn()-1);
            Long days = Duration.between(LocalDateTime.now(), subscriptionEnds).toDays();

            if(lastTwo.size() == 1){
//                    if(days<= 3 && (last.getPayedStatus() || last.getPayUntil().isAfter(LocalDateTime.now()))){
                if(days<= 3){
                    return AllowSubscription.YES;
                }
                else {
                    return AllowSubscription.NO;
                }
            }
            else{
                FacilityInvoice firstToLast = lastTwo.get(1);
                if(days <= 3){
                    if(firstToLast.getPayUntil().isBefore(LocalDateTime.now())
                            && !firstToLast.getPayedStatus()){
                        //should be suspended
                        return AllowSubscription.NO;
                    }
                    else{
                        return AllowSubscription.YES;
                    }
                }
                else{
                    return AllowSubscription.NO;
                }
            }
        }
//        return false;
    }

    @Override
    public void suspendingFacilitiesStatusForUnpaidInvoices() {
        List<FacilityInvoice> invoices = facilityInvoiceService.notPaidInvoicesAfterReliefPeriod(LocalDateTime.now());
        for(FacilityInvoice invoice : invoices){
            invoice.getFacility().setStatus(CustomerStatus.SUSPENDED);
            update(invoice.getFacility());
        }
    }

    @Override
    public void suspendingFacilitiesForExpiredSubscription(){
        List<Facility> activeFacilities = activeFacilities();
        for(Facility f : activeFacilities){
           FacilityInvoice lastInvoice = facilityInvoices(f.getId(), new PageRequest(0, 1)).get(0);
            LocalDateTime expiresAt = lastInvoice.getCreatedAt().plusDays(lastInvoice.getSubscribe().getExpiresIn()-1);
            if(expiresAt.isBefore(LocalDateTime.now())){
                f.setStatus(CustomerStatus.SUSPENDED);
                update(f);
            }
        }
    }

    @Override
    public Double countInvoicesForFacilityByPaidStatus(Long facilityId, boolean status){
        return facilityRepository.countInvoicesForFacilityByPaidStatus(facilityId, status);
    }

    @Override
    public List<Double> facilityInvoicesStats(Long facilityId){
        List<Double> stats = new ArrayList<Double>();
        Double numberOfPaidInvoices = countInvoicesForFacilityByPaidStatus(facilityId, true);
        Double numberOfUnpaidInvoices = countInvoicesForFacilityByPaidStatus(facilityId, false);

        Double numberOfInvoicesForFacility = numberOfPaidInvoices + numberOfUnpaidInvoices;
        Double sumOfPaidInvoices = sumOfInvoicesForFacility(facilityId, true);
        Double sumOdUnpaidInvoices = sumOfInvoicesForFacility(facilityId, false);
        Double percentageOfPaidInvoices = (numberOfPaidInvoices / numberOfInvoicesForFacility) * 100;
        percentageOfPaidInvoices = Math.round(percentageOfPaidInvoices * 100d ) / 100d;

        stats.add(percentageOfPaidInvoices);
        stats.add(sumOfPaidInvoices);
        stats.add(sumOdUnpaidInvoices);
        stats.add(numberOfInvoicesForFacility);

        return stats;
    }

    @Override
    public Double sumOfInvoicesForFacility(Long facilityId, boolean paid){
        return facilityRepository.sumOfInvoicesForFacility(facilityId, paid);
    }

    //api

    @Override
    public List<ApiFacility> getActiveFacilities(){
        return facilityRepository.getActiveFacilities();
    }

    @Override
    public ApiFacilityDetails facilityDetails(Long facilityId) throws NotExisting {
        Facility fac = findOne(facilityId);
        checkFacility(fac, facilityId);
        List<ApiFacilityLocation> locations = facilityRepository.getFacilityLocations(facilityId);
        return new ApiFacilityDetails(fac.getId(), fac.getName(), locations);
    }

    @Override
    public List<ApiMenuItem> facilityCoursesByType(Long facilityId, CourseType type) throws NotExisting {
        Facility fac = findOne(facilityId);
        checkFacility(fac, facilityId);
        return facilityRepository.facilityCoursesByType(facilityId, type);
    }

    @Override
    public List<ApiMenuItem> facilityBeveragesByType(Long facilityId,  BeverageType type) throws NotExisting {
        Facility fac = findOne(facilityId);
        checkFacility(fac, facilityId);
        return facilityRepository.facilityBeveragesByType(facilityId, type);
    }

    @Override
    public List<BeverageType> getBeveragesTypesForFacility(Long facilityId) throws NotExisting {
        Facility fac = findOne(facilityId);
        checkFacility(fac, facilityId);
        return facilityRepository.getBeveragesTypesForFacility(facilityId);
    }

    @Override
    public List<CourseType> getCoursesTypesForFacility(Long facilityId) throws NotExisting{
        Facility fac = findOne(facilityId);
        checkFacility(fac, facilityId);
        return facilityRepository.getCoursesTypesForFacility(facilityId);
    }

    @Override
    public  List<ApiMenuItemDetails> allCoursesForType(CourseType courseType){
        return facilityRepository.allCoursesForType(courseType);
    }

    @Override
    public List<ApiMenuItemDetails> allBeveragesForType(BeverageType beverageType){
        return facilityRepository.allBeveragesForType(beverageType);
    }

    @Override
    public ApiMenuItemDetailsTyped getDetailedMenuItem(Long menuItemId) throws NotExisting {
        Course course = courseService.findOne(menuItemId);
        Beverage beverage = beverageService.findOne(menuItemId);
        if (course != null
                && course.getListedInMenu()
                && course.getFacility().getStatus() == CustomerStatus.ACTIVE) {
            return new ApiMenuItemDetailsTyped(
                    course.getId(),
                    course.getName(),
                    course.getPrice(),
                    course.getDescription(),
                    course.getFacility().getId(),
                    course.getFacility().getName(),
                    course.getType().toString());
        }

        if (beverage != null
                && beverage.getListedInMenu()
                && beverage.getFacility().getStatus() == CustomerStatus.ACTIVE) {
            return new ApiMenuItemDetailsTyped(
                    course.getId(),
                    beverage.getName(),
                    beverage.getPrice(),
                    beverage.getDescription(),
                    beverage.getFacility().getId(),
                    beverage.getFacility().getName(),
                    beverage.getType().toString());
        }
        throw new NotExisting("Menu item with id:" + menuItemId + " is not found");
    }

    private void checkFacility(Facility fac, Long facilityId) throws NotExisting{
        if(fac==null || fac.getStatus()==CustomerStatus.SUSPENDED){
            throw new NotExisting("Facility with id:" + facilityId +" is not found");
        }
    }
}
