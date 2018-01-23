import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Course} from "../../../model/Course";
import {LoginService} from "../login/login.service";

@Injectable()
export class CourseService {

  private courseUrl: string;

  constructor(private http: HttpClient,private loginService:LoginService) {
    this.courseUrl = 'http://localhost:8080/fe/course';
  }

  insertCourse(course: Course){
    return this.http.post(this.courseUrl + '/new', course,{headers:this.loginService.getCredentials()});
  }

  getCourseById(courseId: number){
    return this.http.get<Course>(this.courseUrl + '/get-course/' + courseId,{headers:this.loginService.getCredentials()});
  }

  updateCourse(course: Course){
    return this.http.patch<Course>(this.courseUrl + '/update', course,{headers:this.loginService.getCredentials()});
  }

  getAllFacilityCourses(facilityId: number){
    return this.http.get<Array<Course>>(this.courseUrl + '/facility-courses/' + facilityId,{headers:this.loginService.getCredentials()});
  }

  deleteCourse(courseId: number){
    return this.http.delete(this.courseUrl + '/delete/' + courseId);
  }
}
