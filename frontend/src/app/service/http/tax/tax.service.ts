import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TaxAmount} from "../../../model/TaxAmount";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TaxService {

  private taxUrl: string;

  constructor(private http: HttpClient) {
    this.taxUrl = 'http://localhost:8080/fe/tax';
  }

  getAllTaxesSorted(){
    return this.http.get<Array<TaxAmount>>(this.taxUrl + '/all-taxes-sorted');
  }

  createTax(tax: TaxAmount){
    return this.http.post(this.taxUrl + '/new-tax', tax);
  }

  deleteTax(id: number){
    return this.http.delete(this.taxUrl + '/delete-tax/'+id);
  }

}
