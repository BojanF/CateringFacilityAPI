import { Component, OnInit } from '@angular/core';
import {CourseService} from "../../service/http/course/course.service";
import {Course} from "../../model/Course";
import {Facility} from "../../model/Facility";
import {IdService} from "../../service/id-service/id.service";
import {CourseTypeService} from "../../service/course-type/course-type.service";

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {

  protected newCourse: Course;
  private courseTypes;
  private facilityId: number;
  private facility: Facility;
  private uiState = {
    hiddenLoadingGif: true,
    hiddenError: true,
    hiddenSuccess: true
  };

  constructor(private courseService: CourseService,
              private courseTypeService: CourseTypeService,
              private id: IdService) {
    this.courseTypes = this.courseTypeService.getCourseTypes();
    this.facilityId = this.id.getFacilityId();

    this.newCourse = new Course();
    this.facility = new Facility(this.facilityId);
  }

  ngOnInit() {

  }

  insertCourse(){

    this.uiState = {
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenSuccess: true
    };

    this.newCourse.facility = this.facility;
    this.courseService
      .insertCourse(this.newCourse)
      .subscribe(
        res => {
          console.log("New course successfully added");
          this.uiState = {
            hiddenLoadingGif: true,
            hiddenError: true,
            hiddenSuccess: false
          };
        },
        err => {
          console.log("Error adding new course");
          console.log(err.message);
          this.uiState = {
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenSuccess: true
          };
        }
      )
  }

  resetForm(){
    this.newCourse = new Course();
  }



}
