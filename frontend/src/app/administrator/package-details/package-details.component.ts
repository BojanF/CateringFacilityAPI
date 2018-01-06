import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PackageService} from "../../service/http/package/package.service";
import {SubscriptionPackage} from "../../model/SubscriptionPackage";

@Component({
  selector: 'app-package-details',
  templateUrl: './package-details.component.html',
  styleUrls: ['./package-details.component.css']
})
export class PackageDetailsComponent implements OnInit {

  constructor( private route: ActivatedRoute,
               private packageService: PackageService) { }
  public originalPackage: SubscriptionPackage;
  public updatePackage: SubscriptionPackage;
  public packageStatuses = [];
  public uiState = {
    showPage: true,
    view:{
      hiddenTable: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    form:{
      hiddenLoadingGif: true,
      hiddenError: true,
      hiddenSuccess: true
    }
  };

  ngOnInit() {
    this.originalPackage = new SubscriptionPackage();
    this.updatePackage = new SubscriptionPackage();
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
      this.getSubscriptionPackage(parseInt(id));
    }

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
          this.originalPackage = res;
          this.determineEligiblePackageStatuses(this.originalPackage.status);
          this.updatePackage = new SubscriptionPackage(
            this.originalPackage.id,
            this.originalPackage.name,
            this.originalPackage.price,
            this.originalPackage.expiresIn,
            this.originalPackage.status,
            this.originalPackage.description);
          this.updatePackage.status = null;
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

  updatePackageFn(){
    this.uiState.form = { hiddenLoadingGif: false, hiddenError:true, hiddenSuccess:true };
    this.updatePackage.status = this.updatePackage.status.toUpperCase();
    this.packageService
      .updatePackage(this.updatePackage)
      .subscribe(
        res => {
          console.log("Success");
          console.log(res);
          this.uiState.form = { hiddenLoadingGif: true, hiddenError:true, hiddenSuccess:false };
          this.originalPackage = res;
          this.determineEligiblePackageStatuses(this.originalPackage.status);
          this.updatePackage = new SubscriptionPackage(
            this.originalPackage.id,
            this.originalPackage.name,
            this.originalPackage.price,
            this.originalPackage.expiresIn,
            this.originalPackage.status,
            this.originalPackage.description);
          this.updatePackage.status = null;
        },
        err => {
          console.log("Error occurred");
          this.uiState.form = { hiddenLoadingGif: true, hiddenError:false, hiddenSuccess:true };
        }
      );
  }

  determineStatusCssClass(status: string):string{
    if(status == 'ACTIVE'){
      return 'label label-success';
    }
    else if(status == 'SUSPENDED'){
      return 'label label-warning';
    }
    else{
      return 'label label-danger';
    }
  }

  determineEligiblePackageStatuses(status: string) {
    if (status == "ACTIVE" ) {
      this.packageStatuses = ['Active', 'Suspended'];
    }
    else if (status == "SUSPENDED"){
      this.packageStatuses = ['Active', 'Suspended', 'Defunct'];
    }
    else{
      this.packageStatuses = ['Suspended', 'Defunct'];
    }
  }
}
