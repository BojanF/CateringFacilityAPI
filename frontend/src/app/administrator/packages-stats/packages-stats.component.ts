import { Component, OnInit } from '@angular/core';
import {SubscriptionPackageStats} from "../../model/SubscriptionPackageStats";
import {PackageService} from "../../service/http/package/package.service";

@Component({
  selector: 'app-packages-stats',
  templateUrl: './packages-stats.component.html',
  styleUrls: ['./packages-stats.component.css']
})
export class PackagesStatsComponent implements OnInit {

  private uiState = {
    hiddenTable: true,
    hiddenNoDataInfo: true,
    hiddenErrorMsg: true,
    hiddenLoadingGif: false
  };

  private packagesStats: Array<SubscriptionPackageStats>;
  private page: number = 1;
  public constructor(private packageService: PackageService) {

  }

  ngOnInit() {
    this.getPackagesStats();
  }

  getPackagesStats(){
    this.packageService
      .getPackagesStats()
      .subscribe(
        res => {
          this.packagesStats = res;
          if(this.packagesStats.length > 0){
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
