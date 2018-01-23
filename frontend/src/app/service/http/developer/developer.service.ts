import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginService} from "../login/login.service";

@Injectable()
export class DeveloperService {

  private developerUrl: string;

  constructor(private http: HttpClient,private loginService:LoginService) {
    this.developerUrl = 'http://localhost:8080/fe/developer';
  }

  allowSubscription(developerId: number){
    return this.http.get(this.developerUrl + '/allow-subscription/' + developerId,{headers:this.loginService.getCredentials()});
  }

  developerInvoicesStats(developerId: number){
    return this.http.get<Array<number>>(this.developerUrl + '/invoices-stats/' + developerId,{headers:this.loginService.getCredentials()});
  }

}
