import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PackageService} from "../../service/http/package/package.service";
import {SubscriptionPackage} from "../../model/SubscriptionPackage";
import * as c3 from 'c3';

@Component({
  selector: 'app-package-details',
  templateUrl: './package-details.component.html',
  styleUrls: ['./package-details.component.css']
})
export class PackageDetailsComponent implements OnInit {

  private sumOfPaidInvoices: number;
  private sumOfUnpaidInvoices: number;
  private numberOfInvoicesForPackage: number;
  public originalPackage: SubscriptionPackage;
  public updatePackage: SubscriptionPackage;
  public packageStatuses = [];
  constructor( private route: ActivatedRoute,
               private packageService: PackageService) {
    this.numberOfInvoicesForPackage = 0;
  }


  private uiState = {
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
    },
    stats: {
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenNoInvoicesMsg: true,
      hiddenChart: true
    },
    incomeStats: {
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenTable: true
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
      this.getStats(parseInt(id));
      this.getPackageIncomeStats(parseInt(id));
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

  getStats(id: number){
    this.packageService
      .packageStats(id)
      .subscribe(
        res => {
          console.log(res);
          if(res[2] > 0) {
            this.statsChart(res);
            this.numberOfInvoicesForPackage = res[2];
            this.percentagePaid(res[3]);
            this.uiState.stats = {
              hiddenLoadingGif: true,
              hiddenError: true,
              hiddenNoInvoicesMsg: true,
              hiddenChart: false
            }
          }
          else{
            this.uiState.stats = {
              hiddenLoadingGif: true,
              hiddenError: true,
              hiddenNoInvoicesMsg: false,
              hiddenChart: true
            }
          }
        },
        err => {
          this.uiState.stats = {
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenNoInvoicesMsg: true,
            hiddenChart: true
          }
        }
      );
  }

  percentagePaid(percentage: number){
    let chart = c3.generate({
      bindto: '#percentage-paid',
      data: {
        columns: [
          ['Percentage', percentage]
        ],
        type: 'gauge'
      },
      color: {
        pattern: ['#d9534f', '#F97600', '#F6C600', '#5cb85c'], // the three color levels for the percentage values.
        threshold: {
//            unit: 'value', // percentage is default
//            max: 200, // 100 is default
          values: [30, 60, 90, 100]
        }
      },
      size: {
        height: 180
      }
    });

  }

  statsChart(stats: Array<number>){
    let chart = c3.generate({
      bindto: '#usage-stats',
      data: {
        columns: [
          ['Facilities', stats[0]],
          ['Developers', stats[1]]
        ],
        type : 'donut'
      },
      donut: {
        title: "% of package usage"
      }
    });

    // let chart = c3.generate({
    //   // bindto: '#status-stats',
    //   bindto: '#usage-stats',
    //   data: {
    //     columns: [
    //       ['Active', 1],
    //       ['Suspended', 2],
    //       ['Defunct', 2]
    //     ],
    //     type : 'donut',
    //     colors: {
    //       Active: '#5cb85c',
    //       Suspended: '#f0ad4e',
    //       Defunct: '#d9534f'
    //     },
    //   },
    //   donut: {
    //     title: "Status"
    //   }
    // });
  }

  getPackageIncomeStats(id: number){
    this.packageService
      .packageIncomeStats(id)
      .subscribe(
        res => {
          console.log("Income YES");
          this.uiState.incomeStats = {
            hiddenLoadingGif: true,
            hiddenError: true,
            hiddenTable: false
          };
          this.sumOfPaidInvoices = res[0];
          this.sumOfUnpaidInvoices = res[1];
        },
        err => {
          console.log("Income NO");
          this.uiState.incomeStats = {
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenTable: true
          };
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
      // this.packageStatuses = ['Suspended', 'Defunct'];
      this.packageStatuses = [];
    }
  }
}
