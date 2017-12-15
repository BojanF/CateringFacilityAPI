import { Injectable } from '@angular/core';
import {FacilityInvoice} from "../../../model/FacilityInvoice";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class FacilityInvoiceService {

  constructor(private http: HttpClient) { }

  getAllFacilityInvoicesSorted(){
    return this.http.get<Array<FacilityInvoice>>("http://localhost:8080/fe/fac-invoice/all-sorted");
  }

}
