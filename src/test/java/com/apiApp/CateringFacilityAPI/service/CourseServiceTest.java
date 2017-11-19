package com.apiApp.CateringFacilityAPI.service;

import com.apiApp.CateringFacilityAPI.model.enums.CourseType;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.Course;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CourseServiceTest {

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private ICourseService courseService;

    private Facility fac1;

    @Before
    public void intializeFacility(){
        fac1 = facilityService.insertFacility(
                "Trend",
                "trend",
                "trendPass",
                "trend@email.com",
                CustomerStatus.ACTIVE);
    }

    @Test
    public void crudTestCourse(){
        Course course1 = courseService.insertCourse(
                "Caesar salad",
                120d,
                false,
                 fac1,
                 CourseType.APPETIZER,
                null);
        Assert.assertNotNull(courseService.findOne(course1.getId()));

        Course course2 = courseService.insertCourse(
                "English breakfast",
                150d,
                false,
                 fac1,
                 CourseType.BREAKFAST,
                "Breakfast for champions!!! Eggs, bacon, beans, susage...");
        Assert.assertNotNull(courseService.findOne(course2.getId()));

        Course course3 = courseService.insertCourse(
                "Ambassador cake",
                350d,
                false,
                 fac1,
                 CourseType.DESERT,
                "Recomended for MFAs :)");
        Assert.assertNotNull(courseService.findOne(course3.getId()));

        Course course4 = courseService.insertCourse(
                "Pizza quattro stagioni",
                350d,
                true,
                 fac1,
                 CourseType.MAIN_COURSE,
                "Specialty of the house");
        Assert.assertNotNull(courseService.findOne(course4.getId()));

        Assert.assertNull(course1.getDescription());
        course1.setDescription("Caesar`s favourite salad");
        course1 = courseService.update(course1);
        Assert.assertNotNull(course1.getDescription());
        Assert.assertEquals("Caesar`s favourite salad", course1.getDescription());

        //testing findAll method
        Iterable<Course> allCourses = courseService.findAll();
        List<Long> courseIdentifiers = Arrays.asList(course1.getId(), course2.getId(), course3.getId(), course4.getId());
        for(Course c : allCourses){
            Assert.assertEquals(true, courseIdentifiers.contains(c.getId()));
        }

        courseService.delete(course1.getId());
        courseService.delete(course2.getId());
        courseService.delete(course3.getId());
        courseService.delete(course4.getId());
        Assert.assertNull(courseService.findOne(course1.getId()));
        Assert.assertNull(courseService.findOne(course2.getId()));
        Assert.assertNull(courseService.findOne(course3.getId()));
        Assert.assertNull(courseService.findOne(course4.getId()));

    }

    @After
    public void deleteFacility(){
        facilityService.delete(fac1.getId());
    }
}
