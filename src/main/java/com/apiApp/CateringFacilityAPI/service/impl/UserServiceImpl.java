package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.User;
import com.apiApp.CateringFacilityAPI.model.principal.UserPrincipal;
import com.apiApp.CateringFacilityAPI.persistance.IUserRepository;
import com.apiApp.CateringFacilityAPI.service.IAdministratorService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import com.apiApp.CateringFacilityAPI.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService{

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IFacilityService facilityService;

    @Autowired
    IDeveloperService developerService;

    @Autowired
    IAdministratorService administratorService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);

        return new UserPrincipal(user, facilityService, developerService, administratorService);

    }

    @Override
    public User insertUser(String username, String email, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }
}
