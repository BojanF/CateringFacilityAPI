import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiInvoice} from "../../../model/ApiInvoice";

@Injectable()
export class ApiInvoiceService {

  constructor(private http: HttpClient) { }

  getAllApiInvoicesSorted(){
    return this.http.get<Array<ApiInvoice>>("http://localhost:8080/fe/api-invoice/all-sorted")
  }
}
