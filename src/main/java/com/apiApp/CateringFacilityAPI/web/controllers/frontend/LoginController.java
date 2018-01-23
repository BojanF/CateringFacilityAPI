package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
@CrossOrigin
@SpringBootApplication
@RestController
public class LoginController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
          return user;
    }

}
