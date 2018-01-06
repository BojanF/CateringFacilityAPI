import { Component, OnInit } from '@angular/core';
import {IdService} from "../../service/id-service/id.service";
import {ActivatedRoute} from "@angular/router";
import {CourseTypeService} from "../../service/course-type/course-type.service";
import {CourseService} from "../../service/http/course/course.service";
import {Facility} from "../../model/Facility";
import {Course} from "../../model/Course";

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit {

  private facilityId: number;
  private courseTypes;
  private originalCourse: Course;
  private updateCourse: Course;
  private beverageTypes;
  private facility: Facility;

  constructor(private route: ActivatedRoute,
              private courseTypeService: CourseTypeService,
              private courseService: CourseService,
              private id: IdService) {
    this.facilityId = this.id.getFacilityId();
    this.courseTypes = this.courseTypeService.getCourseTypes();
    this.beverageTypes = this.courseTypeService.getCourseTypes();
    this.originalCourse = new Course();
    this.updateCourse = new Course();
    this.originalCourse.type = '';
    this.facility = new Facility(this.facilityId);
  }

  private uiState = {
    showPage: true,
    view:{
      hiddenTable: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    form:{
      hiddenForm: true,
      hiddenLoadingGif: true,
      hiddenError: true,
      hiddenSuccess: true
    }
  };

  ngOnInit() {

    let id = this.route.snapshot.paramMap.get('id');
    if (!/^\d+$/.test(id) || id=='0') {
      this.uiState.showPage = false;
      console.log("OOOPS msg");
    }
    else{
      this.uiState.showPage = true;
      console.log("show");
      this.getCourse(parseInt(id));
    }
  }

  getCourse(id: number){
    this.courseService
      .getCourseById(id)
      .subscribe(
        res => {
          console.log("Success retrieving course");
          this.originalCourse = res;
          console.log(this.originalCourse);

          this.updateCourse = new Course(
            this.originalCourse.id,
            this.originalCourse.name,
            this.originalCourse.price,
            this.originalCourse.description,
            this.originalCourse.onPromotion,
            this.originalCourse.facility,
            this.originalCourse.type
          );
          this.updateCourse.type = null;

          this.uiState.view = {
            hiddenTable: false,
            hiddenErrorMsg: true,
            hiddenLoadingGif: true
          };

          this.uiState.form.hiddenForm = false;
          this.uiState.form.hiddenLoadingGif = true;

        },
        err => {
          console.log("Error retrieving course");
          if(err.status == 200){
            this.uiState.showPage = false;
          }
          else {
            this.uiState.showPage = true;
          }
          this.uiState.view = {
            hiddenTable: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
          this.uiState.form.hiddenError = false;
        }
      )
  }

  updateCourseSubmit(){
    this.uiState.form = {
      hiddenForm: false,
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenSuccess: true
    };
    this.updateCourse.facility = this.facility;
    this.courseService
      .updateCourse(this.updateCourse)
      .subscribe(
        res => {
          console.log("Course successfully updated");
          this.originalCourse = res;
          this.updateCourse = new Course(
            this.originalCourse.id,
            this.originalCourse.name,
            this.originalCourse.price,
            this.originalCourse.description,
            this.originalCourse.onPromotion,
            this.originalCourse.facility,
            this.originalCourse.type
          );
          this.updateCourse.type = null;

          this.uiState.form = {
            hiddenForm: false,
            hiddenLoadingGif: true,
            hiddenError: true,
            hiddenSuccess: false
          };
        },
        err => {
          console.log("Error updating beverage");
          console.log(err.message);
          this.uiState.form = {
            hiddenForm: false,
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenSuccess: true
          };
        }
      )
  }

  getCourseValue(key: string){
    return this.courseTypeService.getCourseValue(key);
  }

}
