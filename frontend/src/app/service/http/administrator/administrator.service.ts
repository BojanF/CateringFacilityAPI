import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class AdministratorService {

  private administratorUrl: string;
  constructor(private http: HttpClient) {
    this.administratorUrl = 'http://localhost:8080/fe/admin';
  }

  percentageOfPaidInvoices(){
    return this.http.get<Array<number>>(this.administratorUrl + '/paid-invoices-stats');
  }

  invoicesIncomeStats(){
    return this.http.get<Array<number>>(this.administratorUrl + '/income-stats');
  }

}
