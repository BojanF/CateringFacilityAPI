import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Course} from "../../../model/Course";

@Injectable()
export class CourseService {

  private courseUrl: string;

  constructor(private http: HttpClient) {
    this.courseUrl = 'http://localhost:8080/fe/course';
  }

  insertCourse(course: Course){
    return this.http.post(this.courseUrl + '/new', course);
  }

  getCourseById(courseId: number){
    return this.http.get<Course>(this.courseUrl + '/get-course/' + courseId);
  }

  updateCourse(course: Course){
    return this.http.patch<Course>(this.courseUrl + '/update', course);
  }

  getAllFacilityCourses(facilityId: number){
    return this.http.get<Array<Course>>(this.courseUrl + '/facility-courses/' + facilityId);
  }

  deleteCourse(courseId: number){
    return this.http.delete(this.courseUrl + '/delete/' + courseId);
  }
}
