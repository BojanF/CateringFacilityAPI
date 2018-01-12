package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.PackageStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.persistance.ISubscriptionPackageRepository;
import com.apiApp.CateringFacilityAPI.service.ISubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionPackageServiceImpl implements ISubscriptionPackageService {

    @Autowired
    private ISubscriptionPackageRepository packageRepository;

    @Override
    public SubscriptionPackage insertPackage(String name,
                                             Double price,
                                             int expiresIn,
                                             PackageStatus status,
                                             String description) {
        SubscriptionPackage p = new SubscriptionPackage();
        p.setName(name);
        p.setPrice(price);
        p.setExpiresIn(expiresIn);
        p.setStatus(status);
        p.setDescription(description);
        return packageRepository.save(p);
    }

    @Override
    public SubscriptionPackage findOne(Long id) {
        return packageRepository.findOne(id);
    }

    @Override
    public SubscriptionPackage update(SubscriptionPackage _package) {
        return packageRepository.save(_package);
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
    public int countFacilityInvoicesForPackage(Long packageId) {
        return packageRepository.countFacilityInvoicesForPackage(packageId);
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
        List<Integer> result = new ArrayList<Integer>();
        int facInvoices = countFacilityInvoicesForPackage(packageId);
        int apiInvoices = countApiInvoicesForPackage(packageId);
        result.add(facInvoices);
        result.add(apiInvoices);
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
        return  incomeStats;
    }

}
