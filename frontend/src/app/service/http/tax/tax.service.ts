import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TaxAmount} from "../../../model/TaxAmount";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TaxService {

  constructor(private http: HttpClient) { }

  getAllTaxesSorted(){
    return this.http.get<Array<TaxAmount>>('http://localhost:8080/fe/tax/all-taxes-sorted');
  }

  createTax(tax: TaxAmount){
    return this.http.post('http://localhost:8080/fe/tax/new-tax', tax);
  }

  deleteTax(id: string){
    return this.http.delete('http://localhost:8080/fe/tax/delete-tax/'+id);
  }

}
