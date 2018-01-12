import { Component, OnInit } from '@angular/core';
import {SubscriptionPackage} from "../../model/SubscriptionPackage";
import {ActivatedRoute} from "@angular/router";
import {PackageService} from "../../service/http/package/package.service";
import {Facility} from "../../model/Facility";
import {FacilityInvoice} from "../../model/FacilityInvoice";
import {IdService} from "../../service/id-service/id.service";
import {FacilityService} from "../../service/http/facility/facility.service";
import {FacilityInvoiceService} from "../../service/http/facility-invoice/facility-invoice.service";

@Component({
  selector: 'app-fac-get-subscription',
  templateUrl: './fac-get-subscription.component.html',
  styleUrls: ['./fac-get-subscription.component.css']
})
export class FacGetSubscriptionComponent implements OnInit {

  private package: SubscriptionPackage;
  private facilityId: number;
  private newFacilityInvoice: FacilityInvoice;
  private facility: Facility;
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
               private facilityInvoiceService: FacilityInvoiceService,
               private facilityService: FacilityService,
               private id: IdService) {
    this.facilityId = id.getFacilityId();
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
    this.facilityService
      .allowSubscription(this.facilityId)
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
      this.newFacilityInvoice = new FacilityInvoice();
      this.facility = new Facility(this.facilityId);
      this.newFacilityInvoice.subscribe = this.package;
      this.newFacilityInvoice.facility = this.facility;
      let button = $("#subscribe");
      button.html('<img src="../../../assets/pictures/loading.gif" style="width:20px; height:20px;">&nbsp;Subscribing...');
      button.prop('disabled', true);
      this.facilityInvoiceService
        .insertFacilityInvoice(this.newFacilityInvoice)
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
