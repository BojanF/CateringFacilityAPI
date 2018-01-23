import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TaxAmount} from "../../../model/TaxAmount";
import {Observable} from "rxjs/Observable";
import {LoginService} from "../login/login.service";

@Injectable()
export class TaxService {

  private taxUrl: string;

  constructor(private http: HttpClient,private loginService:LoginService) {
    this.taxUrl = 'http://localhost:8080/fe/tax';
  }

  getAllTaxesSorted(){
    return this.http.get<Array<TaxAmount>>(this.taxUrl + '/all-taxes-sorted',{headers:this.loginService.getCredentials()});
  }

  createTax(tax: TaxAmount){
    return this.http.post(this.taxUrl + '/new-tax', tax,{headers:this.loginService.getCredentials()});
  }

  deleteTax(id: number){
    return this.http.delete(this.taxUrl + '/delete-tax/'+id,{headers:this.loginService.getCredentials()});
  }

}
