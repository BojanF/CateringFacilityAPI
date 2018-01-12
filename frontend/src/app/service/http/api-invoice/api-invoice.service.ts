import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiInvoice} from "../../../model/ApiInvoice";

@Injectable()
export class ApiInvoiceService {

  private apiInvoiceUrl: string;

  constructor(private http: HttpClient) {
    this.apiInvoiceUrl = 'http://localhost:8080/fe/api-invoice';
  }

  insertApiInvoice(apiInvoice: ApiInvoice){
    return this.http.post(this.apiInvoiceUrl + '/new', apiInvoice);
  }

  getAllApiInvoicesSorted(){
    return this.http.get<Array<ApiInvoice>>(this.apiInvoiceUrl + '/all-sorted');
  }

  getAllApiInvoicesForDeveloper(id: number){
    return this.http.get<Array<ApiInvoice>>('http://localhost:8080/fe/developer/invoices/'+id);
  }
}
