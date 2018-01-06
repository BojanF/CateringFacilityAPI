package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseRepository extends CrudRepository<Course, Long>{

    @Query(value = "" +
            "SELECT course\n" +
            "FROM com.apiApp.CateringFacilityAPI.model.jpa.Course course\n" +
            "WHERE course.facility.id = :facilityId")
    List<Course> getAllFacilityCourses(@Param("facilityId") Long facilityId);
    
}
