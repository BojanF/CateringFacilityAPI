package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.AllowSubscription;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.persistance.IDeveloperRepository;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeveloperServiceImpl implements IDeveloperService {

    @Autowired
    private IDeveloperRepository developerRepository;

    @Override
    public Developer insertDeveloper(String username, String password, String email, CustomerStatus status) {
        Developer developer = new Developer();
        developer.setUsername(username);
        developer.setPassword(password);
        developer.setEmail(email);
        developer.setStatus(status);
        developer.setUsedTrial(false);
        developer.setRole(Role.ROLE_DEVELOPER);
        return developerRepository.save(developer);
    }

    @Override
    public Developer findOne(Long id) {
        return developerRepository.findOne(id);
    }

    @Override
    public Developer update(Developer developer) {
        return developerRepository.save(developer);
    }

    @Override
    public void delete(Long id) {
        developerRepository.delete(id);
    }

    @Override
    public Iterable<Developer> findAll() {
        return developerRepository.findAll();
    }

    @Override
    public List<ApiInvoice> developerInvoices(Long devId, Pageable page) {
        return developerRepository.developerInvoices(devId, page);
    }

    @Override
    public AllowSubscription allowSubscription(Long developerId) {

        List<ApiInvoice> lastTwo = developerInvoices(developerId, new PageRequest(0, 2));
        if(lastTwo.size() == 0){
            return AllowSubscription.YES;
        }
        else {
            ApiInvoice last = lastTwo.get(0);
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
                ApiInvoice firstToLast = lastTwo.get(1);
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
}
