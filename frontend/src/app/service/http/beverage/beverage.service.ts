import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Beverage} from "../../../model/Beverage";
import {LoginService} from "../login/login.service";

@Injectable()
export class BeverageService {

  private beverageUrl: String;

  constructor(private http: HttpClient,private loginService:LoginService) {
    this.beverageUrl = 'http://localhost:8080/fe/beverage';
  }

  insertBeverage(beverage: Beverage){
    return this.http.post(this.beverageUrl + '/new', beverage,{headers:this.loginService.getCredentials()});
  }

  getBeverageById(beverageId: number){
    return this.http.get<Beverage>(this.beverageUrl + '/get-beverage/' + beverageId,{headers:this.loginService.getCredentials()});
  }

  updateBeverage(beverage: Beverage){
    return this.http.patch<Beverage>(this.beverageUrl + '/update', beverage,{headers:this.loginService.getCredentials()});
  }

  getAllFacilityBeverages(facilityId: number){
    return this.http.get<Array<Beverage>>(this.beverageUrl + '/facility-beverages/' + facilityId,{headers:this.loginService.getCredentials()});
  }

  deleteBeverage(beverageId: number){
    return this.http.delete(this.beverageUrl + '/delete/' + beverageId);
  }

}
