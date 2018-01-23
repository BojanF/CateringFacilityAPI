import { Injectable } from '@angular/core';
import {FacilityLocation} from "../../../model/FacilityLocation";
import {HttpClient} from "@angular/common/http";
import {FacilityLocationContact} from "../../../model/FacilityLocationContact";
import {LoginService} from "../login/login.service";

@Injectable()
export class FacilityLocationService {

  private facilityLocationUrl: string;

  constructor(private http: HttpClient,private loginService:LoginService) {
    this.facilityLocationUrl = 'http://localhost:8080/fe/fac-location';
  }

  insertFacilityLocation(facilityLocation: FacilityLocation){
    return this.http.post(this.facilityLocationUrl + '/new', facilityLocation,{headers:this.loginService.getCredentials()});
  }

  getLocationById(locationId: number){
    return this.http.get<FacilityLocation>(this.facilityLocationUrl + '/get-location/' + locationId,{headers:this.loginService.getCredentials()});
  }

  updateLocation(facilityLocation: FacilityLocation){
    return this.http.patch<FacilityLocation>(this.facilityLocationUrl + '/update', facilityLocation,{headers:this.loginService.getCredentials()});
  }

  facilityLocationContacts(facilityId: number){
    return this.http.get<Array<FacilityLocationContact>>(this.facilityLocationUrl + '/contacts/' + facilityId,{headers:this.loginService.getCredentials()});
  }

  deleteLocation(locationId: number){
    return this.http.delete(this.facilityLocationUrl + '/delete/' + locationId,{headers:this.loginService.getCredentials()});
  }

}
