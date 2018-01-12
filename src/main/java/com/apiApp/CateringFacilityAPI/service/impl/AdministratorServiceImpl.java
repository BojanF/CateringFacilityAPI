package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.Administrator;
import com.apiApp.CateringFacilityAPI.persistance.IAdministratorRepository;
import com.apiApp.CateringFacilityAPI.service.IAdministratorService;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IFacilityInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorServiceImpl implements IAdministratorService {

    @Autowired
    private IAdministratorRepository administratorRepository;

    @Autowired
    private IApiInvoiceService apiInvoiceService;

    @Autowired
    private IFacilityInvoiceService facilityInvoiceService;

    @Override
    public Administrator insertAdmin(String name, String surname, String username, String password, String email) {
        Administrator admin = new Administrator();
        admin.setName(name);
        admin.setSurname(surname);
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setEmail(email);
        admin.setRole(Role.ROLE_ADMIN);
        return administratorRepository.save(admin);
    }


    @Override
    public Administrator findOne(Long id){
       return administratorRepository.findOne(id);
   }

    @Override
    public Administrator update(Administrator admin){
        return administratorRepository.save(admin);
    }

    @Override
    public void delete(Long id){
       administratorRepository.delete(id);
   }

    @Override
    public Iterable<Administrator> findAll(){
       return administratorRepository.findAll();
   }

    @Override
    public List<Double> percentageStats() {
        List<Double> result = new ArrayList<Double>();
        Double apiInvoices = apiInvoiceService.countAllApiInvoices();
        Double facInvoices = facilityInvoiceService.countAllFacilityInvoices();

        Double paidApiInvoices = apiInvoiceService.countPaidApiInvoices();
        Double paidFacInvoices = facilityInvoiceService.countPaidFacilityInvoices();
        Double percentage = ((paidApiInvoices + paidFacInvoices) / (apiInvoices + facInvoices)) * 100;
        percentage = Math.round(percentage * 100d ) / 100d;
        result.add(percentage);
        result.add(apiInvoices);
        result.add(facInvoices);
        return result;
    }

    @Override
    public List<Double> invoicesIncomeStats() {
        List<Double> result = new ArrayList<Double>();
        List<Double> apiInvoicesIncomeStats = apiInvoiceService.apiInvoicesIncomeStats();
        List<Double> facilityInvoicesIncomeStats = facilityInvoiceService.facilityInvoicesIncomeStats();
        result.add(apiInvoicesIncomeStats.get(0)+facilityInvoicesIncomeStats.get(0)); //sum of paid invoices
        result.add(apiInvoicesIncomeStats.get(1)+facilityInvoicesIncomeStats.get(1)); //sum od unpaid invoices
        return result;
    }
}
