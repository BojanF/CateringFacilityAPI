import { Component, OnInit } from '@angular/core';
import {SubscriptionPackage} from "../../model/SubscriptionPackage";
import {PackageService} from "../../service/http/package/package.service";

@Component({
  selector: 'app-dev-view-active-packages',
  templateUrl: './dev-view-active-packages.component.html',
  styleUrls: ['./dev-view-active-packages.component.css']
})
export class DevViewActivePackagesComponent implements OnInit {

 private activePackages:Array<SubscriptionPackage>;
  private page: number = 1;
  private uiState = {
    hiddenTable: true,
    hiddenNoDataInfo: true,
    hiddenErrorMsg: true,
    hiddenLoadingGif: false
  };

  constructor(private packageService: PackageService) { }

  ngOnInit() {
    this.getActivePackages();
  }

  getActivePackages(){
    this.packageService
      .getActivePackages()
      .subscribe(
        res => {
          this.activePackages = res;
          if(this.activePackages.length > 0){
            this.uiState = {
              hiddenTable: false,
              hiddenNoDataInfo: true,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
          else{
            this.uiState = {
              hiddenTable: true,
              hiddenNoDataInfo: false,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }


        },
        err => {
          console.log("Error occurred");
          this.uiState = {
            hiddenTable: true,
            hiddenNoDataInfo: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
        }
      );
  }
}
