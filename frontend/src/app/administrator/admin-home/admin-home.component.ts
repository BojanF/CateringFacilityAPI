import { Component, OnInit } from '@angular/core';
import {AdministratorService} from "../../service/http/administrator/administrator.service";
import * as c3 from 'c3';
import {PackageService} from "../../service/http/package/package.service";

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {


  private sumOfPaidInvoices: number;
  private sumOfUnpaidInvoices: number;

  constructor(private administratorService: AdministratorService,
              private packageService: PackageService) { }
  private uiState = {
    stats1: {
      hiddenLoadingGif: false,
      hiddenError: true
    },
    incomeStats: {
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenTable: true
    },
    stats2: {
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenNoPackagesMsg: true
    },
    stats3: {
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenNoInvoicesMsg: true
    }
  };
  ngOnInit() {
    this.getPercentageOfPaidInvoices();
    this.getIncomeStats();
    this.getPackagesStatusStats();
  }

  getPercentageOfPaidInvoices(){
    this.administratorService
      .percentageOfPaidInvoices()
      .subscribe(
        res => {
          console.log("% YES");

          this.uiState.stats1 = {
            hiddenLoadingGif: true,
            hiddenError: true
          };
          this.paidInvoicesPercentageChart(res[0]);

          if(res[1]+res[2] > 0) {
            this.apiVsFacilityInvoicesChart(res[1], res[2]);
            this.uiState.stats3 = {
              hiddenLoadingGif: true,
              hiddenError: true,
              hiddenNoInvoicesMsg: true
            }
          }
          else{
            this.uiState.stats3 = {
              hiddenLoadingGif: true,
              hiddenError: true,
              hiddenNoInvoicesMsg: false
            }
          }
        },
        err => {
          console.log("% NO");

          this.uiState.stats1 = {
            hiddenLoadingGif: true,
            hiddenError: false
          };

          this.uiState.stats3 = {
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenNoInvoicesMsg: true
          };
        }
      );
  }

  getIncomeStats(){
    this.administratorService
      .invoicesIncomeStats()
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

  getPackagesStatusStats(){
    this.packageService
      .packagesStatusStats()
      .subscribe(
        res => {
          if(res[0] + res[1] + res[2] != 0){
            this.uiState.stats2 = {
              hiddenLoadingGif: true,
              hiddenError: true,
              hiddenNoPackagesMsg: true
            };
            this.packagesStatusChart(res);
          }
          else {
            this.uiState.stats2 = {
              hiddenLoadingGif: true,
              hiddenError: true,
              hiddenNoPackagesMsg: false
            };
          }

        },
        err => {
          this.uiState.stats2 = {
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenNoPackagesMsg: true
          };
        }
      );
  }

  apiVsFacilityInvoicesChart(apiCount:number, facilityCount: number){
    let chart = c3.generate({
      bindto: '#dev-vs-fac',
      data: {
        columns: [
          ['Facilities', facilityCount],
          ['Developers', apiCount]
        ],
        type : 'donut'
      },
      donut: {
        title: "% of invoices"
      }
    });
  }

  paidInvoicesPercentageChart(percentage: number){
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

  packagesStatusChart(stats: Array<number>){
    let chart = c3.generate({
      bindto: '#status-stats',
      data: {
        columns: [
          ['Active', stats[0]],
          ['Suspended', stats[1]],
          ['Defunct', stats[2]]
        ],
        type : 'donut',
        colors: {
          Active: '#5cb85c',
          Suspended: '#f0ad4e',
          Defunct: '#d9534f'
        },
      },
      donut: {
        title: "Status"
      }
    });
  }

}
