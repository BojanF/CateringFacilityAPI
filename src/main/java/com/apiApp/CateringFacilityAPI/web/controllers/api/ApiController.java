package com.apiApp.CateringFacilityAPI.web.controllers.api;

import com.apiApp.CateringFacilityAPI.exceptions.BadRequest;
import com.apiApp.CateringFacilityAPI.exceptions.NotExisting;
import com.apiApp.CateringFacilityAPI.model.api.*;
import com.apiApp.CateringFacilityAPI.exceptions.DeveloperAuth;
import com.apiApp.CateringFacilityAPI.exceptions.SuspendedDeveloper;
import com.apiApp.CateringFacilityAPI.model.enums.BeverageType;
import com.apiApp.CateringFacilityAPI.model.enums.CourseType;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IFacilityLocationService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/{key}/", produces = "application/json")
public class ApiController {

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private IFacilityLocationService facilityLocationService;

    @RequestMapping(value = "/active-facilities", method = RequestMethod.GET)
    public List<ApiFacility> activeFacilities(@PathVariable String key) throws DeveloperAuth, SuspendedDeveloper {
        checkDeveloper(key);
        return facilityService.getActiveFacilities();
    }

    @RequestMapping(value = "/facility-details/{facilityId}", method = RequestMethod.GET)
    public ApiFacilityDetails facilityDetails(@PathVariable String key,
                                              @PathVariable String facilityId) throws SuspendedDeveloper, DeveloperAuth, NotExisting, BadRequest {
        Long facilityIdLong = parseIdToLong(facilityId);
        checkDeveloper(key);
        return facilityService.facilityDetails(facilityIdLong);
    }

    @RequestMapping(value = "/facility-location-contacts/{locationId}", method = RequestMethod.GET)
    public ApiFacilityLocationContacts facilityLocationContacts(@PathVariable String key,
                                                                @PathVariable String locationId) throws SuspendedDeveloper, DeveloperAuth, NotExisting, BadRequest {
        Long locationIdLong = parseIdToLong(locationId);
        checkDeveloper(key);
        return facilityLocationService.contactsForFacilityLocation(locationIdLong);
    }

    @RequestMapping(value = "/facility/{facilityId}/courses/{courseType}")
    public List<ApiMenuItem> facilityCoursesByType(@PathVariable String key,
                                                   @PathVariable String facilityId,
                                                   @PathVariable String courseType) throws BadRequest, SuspendedDeveloper, DeveloperAuth, NotExisting {
        CourseType type = checkCourseType(courseType);
        Long facilityIdLong = parseIdToLong(facilityId);
        checkDeveloper(key);
        return facilityService.facilityCoursesByType(facilityIdLong, type);
    }

    @RequestMapping(value = "/courses/{courseType}")
    public List<ApiMenuItemDetails> allCoursesForType(@PathVariable String key,
                                                      @PathVariable String courseType) throws BadRequest, SuspendedDeveloper, DeveloperAuth {
        CourseType type = checkCourseType(courseType);
        checkDeveloper(key);
        return facilityService.allCoursesForType(type);
    }

    @RequestMapping(value = "/facility/{facilityId}/beverages/{beverageType}")
    public List<ApiMenuItem> facilityBeveragesByType(@PathVariable String key,
                                                     @PathVariable String facilityId,
                                                     @PathVariable String beverageType) throws BadRequest, SuspendedDeveloper, DeveloperAuth, NotExisting {
        BeverageType type = checkBeverageType(beverageType);
        Long facilityIdLong = parseIdToLong(facilityId);
        checkDeveloper(key);
        return facilityService.facilityBeveragesByType(facilityIdLong, type);
    }

    @RequestMapping(value = "/beverages/{beverageType}")
    public List<ApiMenuItemDetails> allBeveragesForType(@PathVariable String key,
                                                        @PathVariable String beverageType) throws BadRequest, SuspendedDeveloper, DeveloperAuth {
        BeverageType type = checkBeverageType(beverageType);
        checkDeveloper(key);
        return facilityService.allBeveragesForType(type);
    }

    @RequestMapping(value = "/facility/{facilityId}/beverage-types-in-menu")
    public List<BeverageType> getBeveragesTypesForFacility(@PathVariable String key,
                                                           @PathVariable String facilityId) throws BadRequest, SuspendedDeveloper, DeveloperAuth, NotExisting {
        Long facilityIdLong = parseIdToLong(facilityId);
        checkDeveloper(key);
        return facilityService.getBeveragesTypesForFacility(facilityIdLong);
    }

    @RequestMapping(value = "/facility/{facilityId}/course-types-in-menu")
    public List<CourseType> getCoursesTypesForFacility(@PathVariable String key,
                                                       @PathVariable String facilityId) throws BadRequest, SuspendedDeveloper, DeveloperAuth, NotExisting {
        Long facilityIdLong = parseIdToLong(facilityId);
        checkDeveloper(key);
        return facilityService.getCoursesTypesForFacility(facilityIdLong);
    }

    @RequestMapping(value = "/detailed-menu-item/{menuItemId}")
    public ApiMenuItemDetailsTyped getDetailedMenuItem(@PathVariable String key,
                                                       @PathVariable String menuItemId) throws BadRequest, SuspendedDeveloper, DeveloperAuth, NotExisting {
        Long menuItemIdLong = parseIdToLong(menuItemId);
        checkDeveloper(key);
        return facilityService.getDetailedMenuItem(menuItemIdLong);
    }

    @ExceptionHandler(NotExisting.class)
    public ErrorResponse notExistingExceptionHandler(Exception ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }

    private void checkDeveloper(String key) throws DeveloperAuth, SuspendedDeveloper {
        Developer dev = developerService.getDeveloperByApiKey(key);
        if(dev == null){
            throw new DeveloperAuth("Invalid API key provided");
        }
        if(dev.getStatus() != CustomerStatus.ACTIVE){
            throw new SuspendedDeveloper("Developer status: " + CustomerStatus.SUSPENDED.toString());
        }
    }

    @ExceptionHandler(DeveloperAuth.class)
    public ErrorResponse userAuthExceptionHandler(Exception ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),ex.getMessage());
    }

    @ExceptionHandler(SuspendedDeveloper.class)
    public ErrorResponse suspendedUserExceptionHandler(Exception ex) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(),ex.getMessage());
    }

    private Long parseIdToLong(String id) throws BadRequest {
        try {
            return Long.parseLong(id);
        }
        catch ( NumberFormatException e) {
            throw new BadRequest("ID should be number!");
        }
    }

    private CourseType checkCourseType(String courseType) throws BadRequest {
        try {
            return CourseType.valueOf(courseType.toUpperCase());
        }
        catch ( IllegalArgumentException e) {
            throw new BadRequest("Wrong course type value! Check the documentation!");
        }
    }

    private BeverageType checkBeverageType(String beverageType) throws BadRequest {
        try {
            return BeverageType.valueOf(beverageType.toUpperCase());
        }
        catch ( IllegalArgumentException e) {
            throw new BadRequest("Wrong beverage type value! Check the documentation!");
        }
    }

    @ExceptionHandler(BadRequest.class)
    public ErrorResponse handleBadRequest(Exception ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }


}
