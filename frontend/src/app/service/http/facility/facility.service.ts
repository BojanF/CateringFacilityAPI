import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Facility} from "../../../model/Facility";
import {FacilityInvoice} from "../../../model/FacilityInvoice";
import {FacilityLocation} from "../../../model/FacilityLocation";

@Injectable()
export class FacilityService {

  private facilityUrl: string;

  constructor(private http: HttpClient,) {
    this.facilityUrl = 'http://localhost:8080/fe/facility';
  }

  getFacilityById(facilityId: number){
    return this.http.get<Facility>(this.facilityUrl + '/get-facility/' + facilityId);
  }

  facilityInvoices(facilityId: number){
    return this.http.get<Array<FacilityInvoice>>(this.facilityUrl + '/invoices/' + facilityId);
  }

  facilityLocations(facilityId: number){
    return this.http.get<Array<FacilityLocation>>(this.facilityUrl + '/locations/' + facilityId);
  }
}
