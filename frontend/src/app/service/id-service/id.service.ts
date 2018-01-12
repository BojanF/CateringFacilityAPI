import { Injectable } from '@angular/core';

@Injectable()
export class IdService {

  //temporary service
  // temoporary service until login is not in function for returning identificators, as logged in users

  private facilityId: number = 12;
  private developerId: number = 1;
  constructor() { }

  getFacilityId(){
    return this.facilityId;
  }

  getDeveloperId(){
    return this.developerId;
  }

}
