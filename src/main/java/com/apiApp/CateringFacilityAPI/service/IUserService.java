package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.User;

public interface IUserService {

    User insertUser(String username, String email, String password, Role role);

    User findOne(Long id);

}
