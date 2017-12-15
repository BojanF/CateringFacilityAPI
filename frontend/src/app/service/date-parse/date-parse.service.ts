import { Injectable } from '@angular/core';

@Injectable()
export class DateParseService {

  constructor() { }

  dateParsing(date) {
    if (date != null) {
      let hour = date.hour.toString();
      if (hour.length == 1) {
        hour = '0' + hour.toString();
      }

      let minutes = date.minute.toString();
      if (minutes.length == 1) {
        minutes = '0' + minutes.toString();
      }
      return date.dayOfMonth + "." +
        date.monthValue + "." +
        date.year + " " +
        hour + ":" +
        minutes;
    }
    else {
      return "Not paid yed !";
    }
  }

}
