package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.Administrator;
import com.apiApp.CateringFacilityAPI.persistance.IAdministratorRepository;
import com.apiApp.CateringFacilityAPI.service.IAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements IAdministratorService {

    @Autowired
    private IAdministratorRepository administratorRepository;

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
}
