package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocation;
import com.apiApp.CateringFacilityAPI.model.jpa.FacilityLocationContact;
import com.apiApp.CateringFacilityAPI.model.jpa.LocationContactId;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityLocationContactRepository;
import com.apiApp.CateringFacilityAPI.service.IFacilityLocationContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacilityLocationContactServiceImpl implements IFacilityLocationContactService {

    @Autowired
    private IFacilityLocationContactRepository contactService;

    @Override
    public FacilityLocationContact insertFacilityLocationContact(LocationContactId id, FacilityLocation location) {
        FacilityLocationContact contact = new FacilityLocationContact();
        contact.setId(id);
        contact.setLocation(location);
        return contactService.save(contact);
    }

    @Override
    public FacilityLocationContact findOne(LocationContactId id) {
        return contactService.findOne(id);
    }

    @Override
    public void delete(LocationContactId id) {
        contactService.delete(id);
    }

    @Override
    public Iterable<FacilityLocationContact> findAll() {
        return contactService.findAll();
    }
}
