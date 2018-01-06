import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FacilityLocationContact} from "../../../model/FacilityLocationContact";

@Injectable()
export class FacilityLocationContactService {

  private facilityLocationContactUrl: string;
  constructor(private http: HttpClient) {
    this.facilityLocationContactUrl = 'http://localhost:8080/fe/location-contact';
  }

  insertLocationContact(locationContact: FacilityLocationContact){
    return this.http.post(this.facilityLocationContactUrl + '/new', locationContact);
  }

  deleteContact(locationId: number, telNumber: string){
    return this.http.delete(this.facilityLocationContactUrl + '/delete/' + locationId + '/' + telNumber);
  }

}
