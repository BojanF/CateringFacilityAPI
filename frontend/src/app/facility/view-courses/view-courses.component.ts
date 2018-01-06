import { Component, OnInit } from '@angular/core';
import {IdService} from "../../service/id-service/id.service";
import {Course} from "../../model/Course";
import {CourseService} from "../../service/http/course/course.service";
import {CourseTypeService} from "../../service/course-type/course-type.service";

@Component({
  selector: 'app-view-courses',
  templateUrl: './view-courses.component.html',
  styleUrls: ['./view-courses.component.css']
})
export class ViewCoursesComponent implements OnInit {

  private facilityId: number;
  private courseTypes;
  protected facilityCourses: Array<Course>;

  constructor(private courseService: CourseService,
              private courseTypeService: CourseTypeService,
              private id: IdService) {
    this.facilityId = this.id.getFacilityId();
    this.courseTypes = this.courseTypeService.getCourseTypes();
  }

  ngOnInit() {
    this.loadCourses();
  }

  public uiState = {
    courseList: {
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    deleteCourse:{
      hiddenDeletingGif: true,
      // hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    }
  };
  private page: number = 1;

  loadCourses(){
    this.uiState.courseList = {
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    };

    //delete beverage UI
    this.uiState.deleteCourse = {
      hiddenDeletingGif: true,
      // hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    };

    this.courseService
      .getAllFacilityCourses(this.facilityId)
      .subscribe(
        res => {
          console.log("Courses retrieved");
          this.facilityCourses = res;
          if(this.facilityCourses.length > 0){
            this.uiState.courseList = {
              hiddenTable: false,
              hiddenNoDataInfo: true,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
          else{
            this.uiState.courseList = {
              hiddenTable: true,
              hiddenNoDataInfo: false,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
        },
        err => {
          console.log("Error retrieving courses");
          this.uiState.courseList = {
            hiddenTable: true,
            hiddenNoDataInfo: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
        }
      )
  }

  deleteCourse(courseId: number){
    this.uiState.deleteCourse ={
      hiddenDeletingGif: false,
      hiddenErrorMsg: true
    };
    this.courseService
      .deleteCourse(courseId)
      .subscribe(
        res => {
          console.log("Successfully deleted beverage");
          this.uiState.deleteCourse = {
            hiddenDeletingGif: true,
            // hiddenSuccessMsg: false,
            hiddenErrorMsg: true
          };
          this.loadCourses();
        },
        err => {
          console.log("Error deleting beverage");
          this.uiState.deleteCourse = {
            hiddenDeletingGif: true,
            // hiddenSuccessMsg: true,
            hiddenErrorMsg: false
          };
          console.log(err.message);
        }
      )
  }

  getCourseValue(key: string){
    return this.courseTypeService.getCourseValue(key);
  }



}
