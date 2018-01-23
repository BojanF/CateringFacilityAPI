package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.custom.MailData;
import com.apiApp.CateringFacilityAPI.events.MailFromScheduledTask;
import com.apiApp.CateringFacilityAPI.model.enums.AllowSubscription;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.model.jpa.User;
import com.apiApp.CateringFacilityAPI.persistance.IDeveloperRepository;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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

    @Autowired
    private IUserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public Developer insertDeveloper(String username, String password, String email) {
        User user = userService.insertUser(username, email, password, Role.DEVELOPER);
        Developer developer = new Developer();
        developer.setStatus(CustomerStatus.SUSPENDED);
        developer.setUsedTrial(false);developer.setUser(user);
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
        String messageContent = "";
        for(ApiInvoice invoice : invoices){
            invoice.getDeveloper().setStatus(CustomerStatus.SUSPENDED);
            update(invoice.getDeveloper());
            messageContent = String.format("<h4>Dear %s </h4> <div> <p> You have unpaid invoice for subscription." +
                    "Now you <strong>CAN NOT </strong> make API request and use our service." +
                    "If you want to continue to use our web service you need to pay your unpaid  invoice." +
                    "If you have active subscription, you <strong> CAN </strong> immediately use our web service after you pay the old invoice</p> </div>" +
                    "Check which invoice you did not paid on our web site" +
                    "<div> <p> Sincerely yours,</p> <p> CateringAPI team. </p></div>" +
                    "</br> <p> *This mail is sent couple of minutes after invoice is generated.</p>", invoice.getDeveloper().getUser().getUsername());
            MailData mailData = new MailData(
                    invoice.getDeveloper().getUser().getEmail(),
                    "Your account on CateringAPI is suspended",
                    messageContent);
            publisher.publishEvent(new MailFromScheduledTask(mailData));
        }
    }

    @Override
    public void suspendingDevelopersForExpiredSubscription(){
        List<Developer> activeDevelopers = activeDevelopers();
        String messageContent = "";
        for(Developer d : activeDevelopers){
            ApiInvoice lastInvoice = developerInvoices(d.getId(), new PageRequest(0, 1)).get(0);
            LocalDateTime expiresAt = lastInvoice.getCreatedAt().plusDays(lastInvoice.getSubscribe().getExpiresIn()-1);
            if(expiresAt.isBefore(LocalDateTime.now())){
                d.setStatus(CustomerStatus.SUSPENDED);
                update(d);
                messageContent = String.format("<h4>Dear %s </h4> <div> <p> Your last subscription made on our platform expired." +
                        "Now you <strong>CAN NOT </strong> make API request and use our service." +
                        "If you want to continue to use our web service subscribe yourself again." +
                        "The moment you wil subscribe your account will be active again, and you <strong> CAN </strong> use our web service.</p> </div>" +
                        "<div> <p> Sincerely yours,</p> <p> CateringAPI team. </p></div>" +
                        "</br> <p> *This mail is sent couple of minutes after invoice is generated.</p>", d.getUser().getUsername());
                MailData mailData = new MailData(
                        d.getUser().getEmail(),
                        "Your account on CateringAPI is suspended",
                        messageContent);
                publisher.publishEvent(new MailFromScheduledTask(mailData));
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

    @Override
    public Developer findDeveloperByUserId(Long userId){
        return developerRepository.findDeveloperByUserId(userId);
    }

    //api
    @Override
    public Developer getDeveloperByApiKey(String key){
        return  developerRepository.getDeveloperByApiKey(key);
    }


}
