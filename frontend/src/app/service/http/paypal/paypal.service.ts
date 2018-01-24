import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RequestOptions} from "@angular/http";
import { HttpHeaders } from '@angular/common/http'
import {IdService} from "../../id-service/id.service";
import {Router} from "@angular/router";

@Injectable()
export class PayPalService {

  private payPalUrl: string;
  private url:string;
  constructor(private http: HttpClient) {
    this.payPalUrl="http://localhost:8080/paypal";
  }

  makePayment(sum) {

    return this.http.post(this.payPalUrl+'/make/payment?sum='+sum, {});

  }

  completePayment(paymentId, payerId) {
    return this.http.post(this.payPalUrl + '/complete/payment?paymentId=' + paymentId + '&payerId=' + payerId , {});
  }
}

