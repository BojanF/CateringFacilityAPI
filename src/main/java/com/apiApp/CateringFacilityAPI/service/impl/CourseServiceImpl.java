package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.model.enums.CourseType;
import com.apiApp.CateringFacilityAPI.model.jpa.Course;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.persistance.ICourseRepository;
import com.apiApp.CateringFacilityAPI.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements ICourseService{

    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public Course insertCourse(String name, Double price, boolean onPromotion, Facility facility, CourseType type, String description) {
        Course course = new Course();
        course.setName(name);
        course.setPrice(price);
        course.setDescription(description);
        course.setOnPromotion(onPromotion);
        course.setFacility(facility);
        course.setType(type);
        return courseRepository.save(course);
    }

    @Override
    public Course findOne(Long id) {
        return courseRepository.findOne(id);
    }

    @Override
    public Course update(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.delete(id);
    }

    @Override
    public Iterable<Course> findAll() {
        return courseRepository.findAll();
    }
}
