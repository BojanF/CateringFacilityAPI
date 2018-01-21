package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.model.jpa.Course;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.service.ICourseService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/fe/course", produces = "application/json")
public class CourseController {
    
    @Autowired
    private ICourseService courseService;
    
    @Autowired
    private IFacilityService facilityService;
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Course insertCourse(@RequestBody Course course){
        Facility facility = facilityService.findOne(course.getFacility().getId());
        return courseService.insertCourse(
                course.getName(),
                course.getPrice(),
                course.getListedInMenu(),
                facility,
                course.getType(),
                course.getDescription());
    }

    @RequestMapping(value = "/get-course/{courseId}", method = RequestMethod.GET)
    public Course getCourseById(@PathVariable Long courseId){
        return courseService.findOne(courseId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public Course updateCourse(@RequestBody Course course){
        Facility facility = facilityService.findOne(course.getFacility().getId());
        course.setFacility(facility);
        return courseService.update(course);
    }

    @RequestMapping(value = "/facility-courses/{facilityId}", method = RequestMethod.GET)
    public List<Course> getAllFacilityCourses(@PathVariable Long facilityId){
        return courseService.getAllFacilityCourses(facilityId);
    }

    @RequestMapping(value = "/delete/{courseId}", method = RequestMethod.DELETE)
    public boolean deleteCourse(@PathVariable Long courseId){
        courseService.delete(courseId);
        if(courseService.findOne(courseId) == null){
            return true;
        }
        else{
            return false;
        }
    }


}
