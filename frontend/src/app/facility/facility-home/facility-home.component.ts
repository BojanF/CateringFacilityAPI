import { Component, OnInit } from '@angular/core';
import {FacilityService} from "../../service/http/facility/facility.service";
import {IdService} from "../../service/id-service/id.service";
import * as c3 from 'c3';

@Component({
  selector: 'app-facility-home',
  templateUrl: './facility-home.component.html',
  styleUrls: ['./facility-home.component.css']
})
export class FacilityHomeComponent implements OnInit {

  private uiState = {
    stats: {
      hiddenLoadingGif: false,
      hiddenStats: true,
      hiddenError: true
    }
  };
  private numberOfInvoices: number;
  private sumOfPaidInvoices: number;
  private sumOfUnpaidInvoices: number;
  private facilityId: number;
  constructor(private facilityService: FacilityService,
              private id: IdService ) {
    this.facilityId = this.id.getFacilityId();
  }

  ngOnInit() {
    this.loadStats();
  }

  loadStats(){
    this.facilityService
      .facilityInvoicesStats(this.facilityId)
      .subscribe(
        res => {
          this.gaugeChart(res[0]);
          this.sumOfPaidInvoices = res[1];
          this.sumOfUnpaidInvoices = res[2];
          this.numberOfInvoices = res[3];
          this.uiState.stats = {
            hiddenLoadingGif: true,
            hiddenStats: false,
            hiddenError: true
          }
        },
        err => {
          this.uiState.stats = {
            hiddenLoadingGif: true,
            hiddenStats: true,
            hiddenError: false
          }
        }
    );
  }

  gaugeChart(percentage: number){
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

}
