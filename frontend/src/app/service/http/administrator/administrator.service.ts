import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginService} from "../login/login.service";

@Injectable()
export class AdministratorService {

  private administratorUrl: string;
  constructor(private http: HttpClient,private loginService:LoginService) {
    this.administratorUrl = 'http://localhost:8080/fe/admin';
  }

  percentageOfPaidInvoices(){
    return this.http.get<Array<number>>(this.administratorUrl + '/paid-invoices-stats',{headers:this.loginService.getCredentials()});
  }

  invoicesIncomeStats(){
    return this.http.get<Array<number>>(this.administratorUrl + '/income-stats',{headers:this.loginService.getCredentials()});
  }

}
