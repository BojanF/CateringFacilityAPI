import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {LoginService} from "../service/http/login/login.service";
import {PayPalService} from "../service/http/paypal/paypal.service";
import {ApiInvoiceService} from "../service/http/api-invoice/api-invoice.service";
import {ApiInvoice} from "../model/ApiInvoice";
import {FacilityInvoiceService} from "../service/http/facility-invoice/facility-invoice.service";

@Component({
  selector: 'paypal',
  templateUrl: './paypal.component.html',
})
export class PayPalComponent implements OnInit {

  private invoice:ApiInvoice;
  private updateApiInvoice;
  private updateFacilityInvoice;
  constructor(private router:Router,
              private paypalService:PayPalService,
              private activatedRoute:ActivatedRoute,
              private apiInvoiceService:ApiInvoiceService,
              private facilityInvoiceService:FacilityInvoiceService) { }


  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      var paymentId = params['paymentId'];
      var payerId = params['PayerID'];


     this.paypalService.completePayment(paymentId,payerId).subscribe(res=>{

       var id = localStorage.paymentId;
       console.log("AAAAAAAAAAA");
       if(localStorage.role=="developer"){
         this.apiInvoiceService.getApiInvoiceById(id).subscribe(res=>{
           this.apiInvoiceService.updateApiInvoice(res.id).subscribe(res=>{
             localStorage.removeItem("paymentId");
             window.location.replace("http://localhost:4200/"+localStorage.role+"/invoices");
           });
         });
       }

       if(localStorage.role=="facility"){
         this.facilityInvoiceService.getFacilityInvoiceById(id).subscribe(res=>{

           this.facilityInvoiceService.updateFacilityInvoice(res.id).subscribe(res=>{
             localStorage.removeItem("paymentId");
             window.location.replace("http://localhost:4200/"+localStorage.role+"/invoices");
           });
       });
       }


      //
     });

    });
  }

}
