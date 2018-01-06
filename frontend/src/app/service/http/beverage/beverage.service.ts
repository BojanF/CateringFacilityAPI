import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Beverage} from "../../../model/Beverage";

@Injectable()
export class BeverageService {

  private beverageUrl: String;

  constructor(private http: HttpClient) {
    this.beverageUrl = 'http://localhost:8080/fe/beverage';
  }

  insertBeverage(beverage: Beverage){
    return this.http.post(this.beverageUrl + '/new', beverage);
  }

  getBeverageById(beverageId: number){
    return this.http.get<Beverage>(this.beverageUrl + '/get-beverage/' + beverageId);
  }

  updateBeverage(beverage: Beverage){
    return this.http.patch<Beverage>(this.beverageUrl + '/update', beverage);
  }

  getAllFacilityBeverages(facilityId: number){
    return this.http.get<Array<Beverage>>(this.beverageUrl + '/facility-beverages/' + facilityId);
  }

  deleteBeverage(beverageId: number){
    return this.http.delete(this.beverageUrl + '/delete/' + beverageId);
  }

}
