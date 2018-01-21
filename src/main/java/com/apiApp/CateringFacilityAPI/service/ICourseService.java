package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CourseType;
import com.apiApp.CateringFacilityAPI.model.jpa.Course;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;

import java.util.List;

public interface ICourseService {

    Course insertCourse(String name,
                        Double price,
                        boolean listedInMenu,
                        Facility facility,
                        CourseType type,
                        String description);

    Course findOne(Long id);

    Course update(Course course);

    void delete(Long id);

    Iterable<Course> findAll();

    //nema unit testovi
    List<Course> getAllFacilityCourses(Long facilityId);
    
}
