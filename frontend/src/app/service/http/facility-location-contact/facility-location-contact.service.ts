import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FacilityLocationContact} from "../../../model/FacilityLocationContact";
import {LoginService} from "../login/login.service";

@Injectable()
export class FacilityLocationContactService {

  private facilityLocationContactUrl: string;
  constructor(private http: HttpClient,private loginService:LoginService) {
    this.facilityLocationContactUrl = 'http://localhost:8080/fe/location-contact';
  }

  insertLocationContact(locationContact: FacilityLocationContact){
    return this.http.post(this.facilityLocationContactUrl + '/new', locationContact,{headers:this.loginService.getCredentials()});
  }

  deleteContact(locationId: number, telNumber: string){
    return this.http.delete(this.facilityLocationContactUrl + '/delete/' + locationId + '/' + telNumber,{headers:this.loginService.getCredentials()});
  }

}
