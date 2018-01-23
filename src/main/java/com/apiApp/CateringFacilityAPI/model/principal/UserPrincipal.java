package com.apiApp.CateringFacilityAPI.model.principal;

import com.apiApp.CateringFacilityAPI.model.enums.Role;
import com.apiApp.CateringFacilityAPI.model.jpa.User;
import com.apiApp.CateringFacilityAPI.persistance.IFacilityRepository;
import com.apiApp.CateringFacilityAPI.service.IAdministratorService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {


    private IFacilityService facilityService;

    private IDeveloperService developerService;

    private IAdministratorService administratorService;

    private User user;


    public UserPrincipal(User user,
                         IFacilityService facilityService,
                         IDeveloperService developerService,
                         IAdministratorService administratorService) {
        this.user = user;
        this.facilityService = facilityService;
        this.developerService = developerService;
        this.administratorService = administratorService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().toString())
        );
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Long getId(){
        if(user.getRole().equals(Role.FACILITY)){
            return facilityService.findFacilityByUserId(user.getId()).getId();
        }
        else if(user.getRole().equals(Role.DEVELOPER)){
            return developerService.findDeveloperByUserId(user.getId()).getId();
        }
        return administratorService.findAdministratorByUserId(user.getId()).getId();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}