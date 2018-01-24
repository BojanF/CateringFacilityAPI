import { Injectable } from '@angular/core';
import {FacilityInvoice} from "../../../model/FacilityInvoice";
import {HttpClient} from "@angular/common/http";
import {LoginService} from "../login/login.service";

@Injectable()
export class FacilityInvoiceService {

  private facilityInvoiceUrl: string;
  constructor(private http: HttpClient,private loginService:LoginService) {
    this.facilityInvoiceUrl = 'http://localhost:8080/fe/fac-invoice';
  }

  insertFacilityInvoice(facilityInvoice: FacilityInvoice){
    return this.http.post(this.facilityInvoiceUrl + '/new', facilityInvoice,{headers:this.loginService.getCredentials()});
  }

  getAllFacilityInvoicesSorted(){
    return this.http.get<Array<FacilityInvoice>>(this.facilityInvoiceUrl + '/all-sorted',{headers:this.loginService.getCredentials()});
  }


  updateFacilityInvoice(invoiceId: number){
    return this.http.patch<FacilityInvoice>(this.facilityInvoiceUrl + '/update/'+invoiceId,{headers:this.loginService.getCredentials()});
  }
  getFacilityInvoiceById(invoiceId: number){
    return this.http.get<FacilityInvoice>(this.facilityInvoiceUrl + '/get-by-id/' + invoiceId,{headers:this.loginService.getCredentials()});}
}
