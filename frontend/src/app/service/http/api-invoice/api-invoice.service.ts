import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiInvoice} from "../../../model/ApiInvoice";
import {LoginService} from "../login/login.service";

@Injectable()
export class ApiInvoiceService {

  private apiInvoiceUrl: string;

  constructor(private http: HttpClient,private loginService:LoginService) {
    this.apiInvoiceUrl = 'http://localhost:8080/fe/api-invoice';
  }

  insertApiInvoice(apiInvoice: ApiInvoice){
    return this.http.post(this.apiInvoiceUrl + '/new', apiInvoice,{headers:this.loginService.getCredentials()});
  }

  getAllApiInvoicesSorted(){
    return this.http.get<Array<ApiInvoice>>(this.apiInvoiceUrl + '/all-sorted',{headers:this.loginService.getCredentials()});
  }

  getAllApiInvoicesForDeveloper(id: number){
    return this.http.get<Array<ApiInvoice>>('http://localhost:8080/fe/developer/invoices/'+id,{headers:this.loginService.getCredentials()});
  }
}
