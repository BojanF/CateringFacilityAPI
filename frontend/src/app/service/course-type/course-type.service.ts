import { Injectable } from '@angular/core';

@Injectable()
export class CourseTypeService {

  private courseTypes;

  constructor() {
    this.courseTypes = [
      {val:'Breakfast', key:'BREAKFAST'},
      {val:'Appetizer', key:'APPETIZER'},
      {val:'Main course', key:'MAIN_COURSE'},
      {val:'Desert', key:'DESERT'}];
  }

  getCourseTypes(){
    return this.courseTypes;
  }

  getCourseValue(key: string){
    if(key == ''){
      return null
    }
    else {
      return this.courseTypes.filter(course => course.key == key).pop().val;
    }
  }

}
