import { Injectable } from '@angular/core';
import {FacilityLocation} from "../../../model/FacilityLocation";
import {HttpClient} from "@angular/common/http";
import {FacilityLocationContact} from "../../../model/FacilityLocationContact";

@Injectable()
export class FacilityLocationService {

  private facilityLocationUrl: string;

  constructor(private http: HttpClient) {
    this.facilityLocationUrl = 'http://localhost:8080/fe/fac-location';
  }

  insertFacilityLocation(facilityLocation: FacilityLocation){
    return this.http.post(this.facilityLocationUrl + '/new', facilityLocation);
  }

  getLocationById(locationId: number){
    return this.http.get<FacilityLocation>(this.facilityLocationUrl + '/get-location/' + locationId);
  }

  updateLocation(facilityLocation: FacilityLocation){
    return this.http.patch<FacilityLocation>(this.facilityLocationUrl + '/update', facilityLocation);
  }

  facilityLocationContacts(facilityId: number){
    return this.http.get<Array<FacilityLocationContact>>(this.facilityLocationUrl + '/contacts/' + facilityId);
  }

  deleteLocation(locationId: number){
    return this.http.delete(this.facilityLocationUrl + '/delete/' + locationId);
  }

}
