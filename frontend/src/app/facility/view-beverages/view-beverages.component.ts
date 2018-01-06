import { Component, OnInit } from '@angular/core';
import {Facility} from "../../model/Facility";
import {Beverage} from "../../model/Beverage";
import {BeverageService} from "../../service/http/beverage/beverage.service";
import {IdService} from "../../service/id-service/id.service";
import {BeverageTypeService} from "../../service/beverage-type/beverage-type.service";

@Component({
  selector: 'app-view-beverages',
  templateUrl: './view-beverages.component.html',
  styleUrls: ['./view-beverages.component.css']
})
export class ViewBeveragesComponent implements OnInit {

  private facilityId: number;
  private facility: Facility;
  protected facilityBeverages: Array<Beverage>;

  constructor(private beverageService: BeverageService,
              private beverageTypeService: BeverageTypeService,
              private id: IdService) {

    this.facilityId = this.id.getFacilityId();

  }

  ngOnInit() {
    this.loadBeverages();
  }

  private uiState = {
    beverageList: {
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    deleteBeverage:{
      hiddenDeletingGif: true,
      // hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    }
  };
  private page: number = 1;

  loadBeverages(){
    this.uiState.beverageList = {
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    };

    //delete beverage UI
    this.uiState.deleteBeverage = {
      hiddenDeletingGif: true,
      // hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    };

    this.beverageService
      .getAllFacilityBeverages(this.facilityId)
      .subscribe(
        res => {
          console.log("Beverages retrieved");
          this.facilityBeverages = res;
          if(this.facilityBeverages.length > 0){
            this.uiState.beverageList = {
              hiddenTable: false,
              hiddenNoDataInfo: true,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
          else{
            this.uiState.beverageList = {
              hiddenTable: true,
              hiddenNoDataInfo: false,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
        },
        err => {
          console.log("Error retrieving beverages");
          this.uiState.beverageList = {
            hiddenTable: true,
            hiddenNoDataInfo: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
        }
      )
  }

  deleteBeverage(beverageId: number){
    this.uiState.deleteBeverage ={
      hiddenDeletingGif: false,
      hiddenErrorMsg: true
    };
    this.beverageService
      .deleteBeverage(beverageId)
      .subscribe(
        res => {
          console.log("Successfully deleted beverage");
          this.uiState.deleteBeverage = {
            hiddenDeletingGif: true,
            // hiddenSuccessMsg: false,
            hiddenErrorMsg: true
          };
          this.loadBeverages();
        },
        err => {
          console.log("Error deleting beverage");
          this.uiState.deleteBeverage = {
            hiddenDeletingGif: true,
            // hiddenSuccessMsg: true,
            hiddenErrorMsg: false
          };
          console.log(err.message);
        }
      )
  }

  getBeverageValue(key: string){
    return this.beverageTypeService.getBeverageValue(key);
  }
}
