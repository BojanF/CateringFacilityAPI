package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.components.IEmailService;
import com.apiApp.CateringFacilityAPI.custom.SubscriptionPackageStats;
import com.apiApp.CateringFacilityAPI.events.NewPackageSendMail;
import com.apiApp.CateringFacilityAPI.events.UpdatedPackageSendMail;
import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.*;
import com.apiApp.CateringFacilityAPI.persistance.ISubscriptionPackageRepository;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import com.apiApp.CateringFacilityAPI.service.ISubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionPackageServiceImpl implements ISubscriptionPackageService {

    @Autowired
    private ISubscriptionPackageRepository packageRepository;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public SubscriptionPackage insertPackage(String name,
                                             Double price,
                                             int expiresIn,
                                             String description) {
        SubscriptionPackage p = new SubscriptionPackage();
        p.setName(name);
        p.setPrice(price);
        p.setExpiresIn(expiresIn);
        p.setStatus(PackageStatus.ACTIVE);
        p.setDescription(description);
        p.setSendMail(true);
        packageRepository.save(p);
        String packageInfo = String.format("<p>From today you can subscribe yourself on the <strong>%s</strong> package on the platform.</p>" +
                "<table border=\"3\"> " +
                "<tr> <th> Price: </th> <td> %s &euro;</td> </tr>" +
                "<tr> <th> Expires in: </th> <td> %s days</td> </tr>" +
                "</table>\n" +
                "<h4> Description: </h4>" +
                "<p> %s </p> <br/>", p.getName(), p.getPrice(), p.getExpiresIn(), p.getDescription());
        publisher.publishEvent(new NewPackageSendMail(packageInfo));
        return p;
    }

    @Override
    public SubscriptionPackage findOne(Long id) {
        return packageRepository.findOne(id);
    }

    @Override
    public SubscriptionPackage update(SubscriptionPackage updatedPackage) {
        PackageStatus notUpdated = findOne(updatedPackage.getId()).getStatus();
        updatedPackage = packageRepository.save(updatedPackage);
        PackageStatus updatedStatus = updatedPackage.getStatus();
        if(notUpdated != updatedStatus){
            System.out.println("Send updating mails");
            String packageInfo = "";
            if(updatedStatus == PackageStatus.DEFUNCT){
                packageInfo = String.format("From today package <strong>%s</strong> is <strong>NOT AVAILABLE</strong> for subscription.\n" +
                        "And it will <strong> NEVER </strong> be available in the future" +
                        "If you were subscribed to this package, check our subscription packages offer and chose different package to subscribe." +
                        "Your current subscription will continue to be active although this package is scrapped.\n", updatedPackage.getName());
            }
            else if(updatedStatus == PackageStatus.SUSPENDED){
                packageInfo = String.format("From today package <strong>%s</strong> temporarily is not available for subscription." +
                        "Maybe one day will be active again." +
                        "If you are subscribed to this package your current subscription will continue to be active although this package is temporarily not available." +
                        "Meanwhile you can chose from currently active packages on the platform.", updatedPackage.getName());
            }
            else{
                packageInfo = String.format("<p>From today package <strong>%s</strong> is <strong>AGAIN AVAILABLE</strong> for subscription!</p>" +
                        "<table border=\"3\"> " +
                        "<tr> <th> Price: </th> <td> %s &euro;</td> </tr>" +
                        "<tr> <th> Expires in: </th> <td> %s days</td> </tr>" +
                        "</table>\n" +
                        "<h4> Description: </h4>" +
                        "<p> %s </p> <br/>", updatedPackage.getName(), updatedPackage.getPrice(), updatedPackage.getExpiresIn(), updatedPackage.getDescription());
            }
            publisher.publishEvent(new UpdatedPackageSendMail(packageInfo));
        }
        return updatedPackage;
    }

    @Override
    public void delete(Long id) {
        packageRepository.delete(id);
    }

    @Override
    public Iterable<SubscriptionPackage> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public List<ApiInvoice> ApiInvoicesForPackage(Long packageId) {
        return packageRepository.ApiInvoicesForPackage(packageId);
    }

    @Override
    public List<FacilityInvoice> FacilityInvoicesForPackage(Long packageId) {
        return packageRepository.FacilityInvoicesForPackage(packageId);
    }

    @Override
    public int countApiInvoicesForPackage(Long packageId) {
        return packageRepository.countApiInvoicesForPackage(packageId);
    }

    @Override
    public int countApiInvoicesForPackageByPaidStatus(Long packageId, boolean status){
        return packageRepository.countApiInvoicesForPackageByPaidStatus(packageId, status);
    }

    @Override
    public int countFacilityInvoicesForPackage(Long packageId) {
        return packageRepository.countFacilityInvoicesForPackage(packageId);
    }

    @Override
    public int countFacilityInvoicesForPackageByPaidStatus(Long packageId, boolean status){
        return  packageRepository.countFacilityInvoicesForPackageByPaidStatus(packageId, status);
    }

    @Override
    public Double sumOfInvoicesForPackage(Long packageId, boolean paid) {
        return packageRepository.sumOfApiInvoicesForPackage(packageId, paid) + packageRepository.sumOfFacilityInvoicesForPackage(packageId, paid);
    }

    @Override
    public List<SubscriptionPackage> getActivePackages() {
        return packageRepository.getActivePackages();
    }

    @Override
    public List<Integer> packageStats(Long packageId) {
        //data used for
        //percentage of paid invoices for package
        //percentage of how much invoices for one package are from developers, how much percents from facilities
        List<Integer> result = new ArrayList<Integer>();
        int facInvoices = countFacilityInvoicesForPackage(packageId);
        int apiInvoices = countApiInvoicesForPackage(packageId);
        int numberOfInvoicesForPackage = facInvoices + apiInvoices;
        int numOfPaidApiInvoices = countApiInvoicesForPackageByPaidStatus(packageId, true);
        int numOfPaidFacilityInvoices = countFacilityInvoicesForPackageByPaidStatus(packageId, true);
        result.add(facInvoices);//number of facility invoices for package
        result.add(apiInvoices);//number of developer invoices for package
        result.add(numberOfInvoicesForPackage);//number of total invoices for package
        double percent = ((numOfPaidApiInvoices + numOfPaidFacilityInvoices) / (double)numberOfInvoicesForPackage)*100d;
        result.add((int)percent);//%of paid invoices for package
        return result;
    }

    @Override
    public List<Integer> packagesStatusStats() {
        List<Integer> result = new ArrayList<Integer>();
        int active = packageRepository.countPackagesWithStatus(PackageStatus.ACTIVE);
        int suspended = packageRepository.countPackagesWithStatus(PackageStatus.SUSPENDED);
        int defunct = packageRepository.countPackagesWithStatus(PackageStatus.DEFUNCT);
        result.add(active);
        result.add(suspended);
        result.add(defunct);
        return result;
    }

    @Override
    public List<Double> packageIncomeStats(Long packageId){
        List<Double> incomeStats = new ArrayList<Double>();
        Double sumPaidInvoices = sumOfInvoicesForPackage(packageId, true);
        Double sumUnpaidInvoices = sumOfInvoicesForPackage(packageId, false);
        incomeStats.add(sumPaidInvoices);
        incomeStats.add(sumUnpaidInvoices);
        return incomeStats;
    }

    @Override
    public List<SubscriptionPackage> packagesForMailSending(){
        return packageRepository.packagesForMailSending();
    }

    @Override
    public List<SubscriptionPackageStats> subscriptionPackagesStats(){
        List<SubscriptionPackageStats> result = new ArrayList<SubscriptionPackageStats>();
        Iterable<SubscriptionPackage> packages = findAll();
        for(SubscriptionPackage sp : packages){
            List<Double> sums = packageIncomeStats(sp.getId());
            result.add(new SubscriptionPackageStats(
                    sp.getId(),
                    sp.getName(),
                    sums.get(0),
                    sums.get(1),
                    sp.getStatus()));
        }
        return result;
    }
}
