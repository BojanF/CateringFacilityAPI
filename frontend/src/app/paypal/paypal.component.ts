import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {LoginService} from "../service/http/login/login.service";
import {PayPalService} from "../service/http/paypal/paypal.service";

@Component({
  selector: 'paypal',
  templateUrl: './paypal.component.html',
})
export class PayPalComponent implements OnInit {

  constructor(private router:Router,private paypalService:PayPalService,private activatedRoute:ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      var paymentId = params['paymentId'];
      var payerId = params['PayerID'];
      var token = params['token'];

     this.paypalService.completePayment(paymentId,payerId);
     this.router.navigateByUrl(localStorage.role+"/invoices")
      // Print the parameter to the console.
    });

  }



}
