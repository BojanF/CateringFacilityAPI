import { Injectable } from '@angular/core';
import {FacilityInvoice} from "../../../model/FacilityInvoice";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class FacilityInvoiceService {

  private facilityInvoiceUrl: string;
  constructor(private http: HttpClient) {
    this.facilityInvoiceUrl = 'http://localhost:8080/fe/fac-invoice';
  }

  insertFacilityInvoice(facilityInvoice: FacilityInvoice){
    return this.http.post(this.facilityInvoiceUrl + '/new', facilityInvoice);
  }

  getAllFacilityInvoicesSorted(){
    return this.http.get<Array<FacilityInvoice>>(this.facilityInvoiceUrl + '/all-sorted');
  }

}
