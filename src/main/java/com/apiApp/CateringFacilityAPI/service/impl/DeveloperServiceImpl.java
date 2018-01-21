package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.AllowSubscription;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.persistance.IDeveloperRepository;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeveloperServiceImpl implements IDeveloperService {

    @Autowired
    private IDeveloperRepository developerRepository;

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    @Override
    public Developer insertDeveloper(String username, String password, String email) {
        Developer developer = new Developer();
        developer.setUsername(username);
        developer.setPassword(password);
        developer.setEmail(email);
        developer.setStatus(CustomerStatus.SUSPENDED);
        developer.setUsedTrial(false);
        developer.setRole(Role.DEVELOPER);
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
    public List<Developer> activeDevelopers(){
        return developerRepository.activeDevelopers();
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

    @Override
    public void suspendingDevelopersStatusForUnpaidInvoices() {
        List<ApiInvoice> invoices = apiInvoiceService.notPaidInvoicesAfterReliefPeriod(LocalDateTime.now());
        for(ApiInvoice invoice : invoices){
            invoice.getDeveloper().setStatus(CustomerStatus.SUSPENDED);
            update(invoice.getDeveloper());
        }
    }

    @Override
    public void suspendingDevelopersForExpiredSubscription(){
        List<Developer> activeDevelopers = activeDevelopers();
        for(Developer d : activeDevelopers){
            ApiInvoice lastInvoice = developerInvoices(d.getId(), new PageRequest(0, 1)).get(0);
            LocalDateTime expiresAt = lastInvoice.getCreatedAt().plusDays(lastInvoice.getSubscribe().getExpiresIn()-1);
            if(expiresAt.isBefore(LocalDateTime.now())){
                d.setStatus(CustomerStatus.SUSPENDED);
                update(d);
            }
        }
    }

    @Override
    public Double countInvoicesForDeveloperByPaidStatus(Long developerId, boolean status){
        return developerRepository.countInvoicesForDeveloperByPaidStatus(developerId, status);
    }

    @Override
    public List<Double>developerInvoicesStats(Long developerId){
        List<Double> stats = new ArrayList<Double>();
        Double numberOfPaidInvoices = countInvoicesForDeveloperByPaidStatus(developerId, true);
        Double numberOfUnpaidInvoices = countInvoicesForDeveloperByPaidStatus(developerId, false);

        Double numberOfInvoicesForDeveloper = numberOfPaidInvoices + numberOfUnpaidInvoices;
        Double sumOfPaidInvoices = sumOfInvoicesForDeveloper(developerId, true);
        Double sumOdUnpaidInvoices = sumOfInvoicesForDeveloper(developerId, false);
        Double percentageOfPaidInvoices = (numberOfPaidInvoices / numberOfInvoicesForDeveloper) * 100;
        percentageOfPaidInvoices = Math.round(percentageOfPaidInvoices * 100d ) / 100d;

        stats.add(percentageOfPaidInvoices);
        stats.add(sumOfPaidInvoices);
        stats.add(sumOdUnpaidInvoices);
        stats.add(numberOfInvoicesForDeveloper);

        return stats;
    }

    @Override
    public Double sumOfInvoicesForDeveloper(Long developerId, boolean paid){
        return developerRepository.sumOfInvoicesForDeveloper(developerId, paid);
    }

    //api
    @Override
    public Developer getDeveloperByApiKey(String key){
        return  developerRepository.getDeveloperByApiKey(key);
    }


}
