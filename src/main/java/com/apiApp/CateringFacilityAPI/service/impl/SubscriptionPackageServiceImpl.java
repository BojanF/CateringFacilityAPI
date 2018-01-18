package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.components.IEmailService;
import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.*;
import com.apiApp.CateringFacilityAPI.persistance.ISubscriptionPackageRepository;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import com.apiApp.CateringFacilityAPI.service.ISubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return packageRepository.save(p);
    }

    @Override
    public SubscriptionPackage findOne(Long id) {
        return packageRepository.findOne(id);
    }

    @Override
    public SubscriptionPackage update(SubscriptionPackage updatedPackage) {
        SubscriptionPackage notUpdated = findOne(updatedPackage.getId());
        if(notUpdated.getStatus() != updatedPackage.getStatus()){
            updatedPackage.setSendMail(true);
        }
        return packageRepository.save(updatedPackage);
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
    public void sendingMailsForSubscriptionPackagesUpdates(){
        List<SubscriptionPackage> packagesForMailSending = packagesForMailSending();
        int x =0;
        if(packagesForMailSending.size() !=0 ) {
            Iterable<Developer> developers = developerService.findAll();
            Iterable<Facility> facilities = facilityService.findAll();
            for (SubscriptionPackage p : packagesForMailSending) {
                PackageStatus status = p.getStatus();
                String packageInfo = "";
                String messageContent = "";
                String messageSubject = "Subscription package info";
                if (status == PackageStatus.ACTIVE) {
                    packageInfo = String.format("<p>From today you can subscribe yourself on the <strong>%s</strong> package on the platform.</p>" +
                            "<table border=\"3\"> " +
                                "<tr> <th> Price: </th> <td> %s &euro;</td> </tr>" +
                                "<tr> <th> Expires in: </th> <td> %s </td> </tr>" +
                            "</table>\n" +
                            "<h4> Description: </h4>" +
                            "<p> %s </p> <br/>", p.getName(), p.getPrice(), p.getExpiresIn(), p.getDescription());
                } else if (status == PackageStatus.SUSPENDED) {
                    packageInfo = String.format("From today package <strong>%s</strong> temporarily is not available for subscription." +
                            "Maybe one day will be active again." +
                            "Meanwhile you can chose from currently active packages on the platform.", p.getName());
                } else {
                    packageInfo = String.format("From today package <strong>%s</strong> is not available for subscription.\n" +
                            "And it will never be available in the future", p.getName());
                }

                for(Developer d : developers){
                    messageContent = String.format("<h3>Dear %s</h3> <div><p> %s </p> <p> Sincerely yours,\n CateringAPI team. </p> </div>", d.getUsername(), packageInfo);
                    emailService.sendSimpleMessage(d.getEmail(), messageSubject, messageContent);
                }

                for(Facility f : facilities){
                    String.format(messageContent, "<h3>Dear %s</h3> <div> %s </div> <div> Sincerely yours CateringAPI team. </div>", f.getUsername(), packageInfo);
                    emailService.sendSimpleMessage(f.getEmail(), messageSubject, messageContent);
                }
                p.setSendMail(false);
                update(p);
            }
        }
    }

}
