import {Component, OnInit} from '@angular/core';
import {SubscriptionPackage} from "../../model/SubscriptionPackage";
import {PackageService} from "../../service/http/package/package.service";

@Component({
  selector: 'app-view-packages',
  templateUrl: './view-packages.component.html',
  styleUrls: ['./view-packages.component.css']
})
export class ViewPackagesComponent implements OnInit {

  uiState = {
    hiddenTable: true,
    hiddenNoDataInfo: true,
    hiddenErrorMsg: true,
    hiddenLoadingGif: false
  };

  private data:Array<SubscriptionPackage>;
  public page: number = 1;
  public constructor(private packageService: PackageService) {

  }

  ngOnInit() {
    this.packageService
      .getAllPackages()
      .subscribe(
        res => {
          this.data = res;
          if(this.data.length > 0){
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

  determineCssClass(status: string){
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

}
