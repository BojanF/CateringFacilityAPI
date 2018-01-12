import { Component, OnInit } from '@angular/core';
import {SubscriptionPackage} from "../../model/SubscriptionPackage";
import {ActivatedRoute} from "@angular/router";
import {PackageService} from "../../service/http/package/package.service";
import {IdService} from "../../service/id-service/id.service";
import {ApiInvoiceService} from "../../service/http/api-invoice/api-invoice.service";
import {ApiInvoice} from "../../model/ApiInvoice";
import {Developer} from "../../model/Developer";
import * as $ from "jquery";
import {DeveloperService} from "../../service/http/developer/developer.service";

@Component({
  selector: 'app-dev-get-subscription',
  templateUrl: './dev-get-subscription.component.html',
  styleUrls: ['./dev-get-subscription.component.css']
})
export class DevGetSubscriptionComponent implements OnInit {

  private package: SubscriptionPackage;
  private developerId: number;
  private newApiInvoice: ApiInvoice;
  private developer: Developer;
  private allowSubscription: boolean;
  private uiState = {
    showPage: true,
    view:{
      hiddenTable: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    subscribe:{
      hiddenSubscribeButton: false,
      hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    }
  };

  constructor( private route: ActivatedRoute,
               private packageService: PackageService,
               private apiInvoiceService: ApiInvoiceService,
               private developerService: DeveloperService,
               private id: IdService) {
    this.developerId = id.getDeveloperId();
    this.allowSubscription = false;
  }


  ngOnInit() {
    this.package = new SubscriptionPackage();
    let id = this.route.snapshot.paramMap.get('id');
    if(id==null || id==undefined || id==''){
      id = '0';
    }
    if (!/^\d+$/.test(id) || id=='0') {
      this.uiState.showPage = false;
      console.log("OOOPS msg");
    }
    else{
      this.uiState.showPage = true;
      console.log("show");
      this.allowSubscriptionFn();
      this.getSubscriptionPackage(parseInt(id));
    }
  }

  allowSubscriptionFn(){
    this.developerService
      .allowSubscription(this.developerId)
      .subscribe(
        res => {
          console.log("permission fetched");
          console.log(res);
          if(res == 'YES'){
            this.allowSubscription = true;
          }
          else{
            this.allowSubscription = false;
          }
        },
        err =>{
          console.log("error fetching permission");
          this.allowSubscription = false;
        }
      );
  }

  getSubscriptionPackage(id: number){

    console.log(id);
    this.uiState.view = {
      hiddenTable: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    };
    this.packageService
      .getPackageById(id)
      .subscribe(
        res => {
          console.log("Success");
          console.log(res);
          this.package = res;

          this.uiState.view = {
            hiddenTable: false,
            hiddenErrorMsg: true,
            hiddenLoadingGif: true
          };
        },
        err => {
          console.log("Error occurred");
          console.log(err.status);
          if(err.status == 200){
            this.uiState.showPage = false;
          }
          else {
            this.uiState.showPage = true;
            this.uiState.view = {
              hiddenTable: true,
              hiddenErrorMsg: false,
              hiddenLoadingGif: true
            };
          }
        }
      );
  }

  subscribe(){
    if(this.allowSubscription) {
      this.uiState.view.hiddenErrorMsg = true;
      this.newApiInvoice = new ApiInvoice();
      this.developer = new Developer(this.developerId);
      this.newApiInvoice.subscribe = this.package;
      this.newApiInvoice.developer = this.developer;
      let button = $("#subscribe");
      button.html('<img src="../../../assets/pictures/loading.gif" style="width:20px; height:20px;">&nbsp;Subscribing...');
      button.prop('disabled', true);
      this.apiInvoiceService
        .insertApiInvoice(this.newApiInvoice)
        .subscribe(
          res => {
            console.log("Inserted invoices");
            this.uiState.subscribe.hiddenSuccessMsg = false;
            this.uiState.subscribe.hiddenSubscribeButton = true;
            this.allowSubscription = false;
          },
          err => {
            console.log("Error insertion");
            this.uiState.view.hiddenErrorMsg = false;
            button.html('<i class="fa fa=fw fa-paper-plane-o" aria-hidden="true"> </i> Subscribe');
            button.prop('disabled', false);
          }
        );
    }
  }
}
