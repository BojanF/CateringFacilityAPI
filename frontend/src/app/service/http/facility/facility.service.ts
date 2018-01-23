import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Facility} from "../../../model/Facility";
import {FacilityInvoice} from "../../../model/FacilityInvoice";
import {FacilityLocation} from "../../../model/FacilityLocation";
import {LoginService} from "../login/login.service";

@Injectable()
export class FacilityService {

  private facilityUrl: string;

  constructor(private http: HttpClient,private loginService:LoginService) {
    this.facilityUrl = 'http://localhost:8080/fe/facility';
  }

  getFacilityById(facilityId: number){
    return this.http.get<Facility>(this.facilityUrl + '/get-facility/' + facilityId,{headers:this.loginService.getCredentials()});
  }

  facilityInvoices(facilityId: number){
    return this.http.get<Array<FacilityInvoice>>(this.facilityUrl + '/invoices/' + facilityId,{headers:this.loginService.getCredentials()});
  }

  facilityLocations(facilityId: number){
    return this.http.get<Array<FacilityLocation>>(this.facilityUrl + '/locations/' + facilityId,{headers:this.loginService.getCredentials()});
  }

  allowSubscription(facilityId: number){
    return this.http.get(this.facilityUrl + '/allow-subscription/' + facilityId,{headers:this.loginService.getCredentials()});
  }

  facilityInvoicesStats(facilityId: number){
    return this.http.get<Array<number>>(this.facilityUrl + '/invoices-stats/' + facilityId,{headers:this.loginService.getCredentials()});
  }
}
