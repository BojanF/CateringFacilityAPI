import { Component, OnInit } from '@angular/core';
import {DeveloperService} from "../../service/http/developer/developer.service";
import {IdService} from "../../service/id-service/id.service";
import * as c3 from 'c3';
import {BeverageTypeService} from "../../service/beverage-type/beverage-type.service";
import {CourseTypeService} from "../../service/course-type/course-type.service";

@Component({
  selector: 'app-dev-home',
  templateUrl: './dev-home.component.html',
  styleUrls: ['./dev-home.component.css']
})
export class DevHomeComponent implements OnInit {

  private uiState = {
    stats: {
      hiddenLoadingGif: false,
      hiddenStats: true,
      hiddenError: true
    }
  };

  private beverageTypes = [];
  private courseTypes = [];
  private numberOfInvoices: number;
  private sumOfPaidInvoices: number;
  private sumOfUnpaidInvoices: number;
  private developerId: number;
  constructor(private developerService: DeveloperService,
              private id: IdService,
              private beverageTypeService: BeverageTypeService,
              private courseTypeService: CourseTypeService) {
    this.developerId = id.getDeveloperId();
    this.beverageTypes = this.beverageTypeService.getTypes();
    this.courseTypes = this.courseTypeService.getCourseTypes();
  }

  ngOnInit() {
    this.loadStats();
  }

  loadStats(){
    this.developerService
      .developerInvoicesStats(this.developerId)
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
