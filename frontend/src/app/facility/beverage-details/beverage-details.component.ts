import { Component, OnInit } from '@angular/core';
import {BeverageService} from "../../service/http/beverage/beverage.service";
import {Beverage} from "../../model/Beverage";
import {ActivatedRoute} from "@angular/router";
import {BeverageTypeService} from "../../service/beverage-type/beverage-type.service";
import {Facility} from "../../model/Facility";
import {IdService} from "../../service/id-service/id.service";

@Component({
  selector: 'app-beverage-details',
  templateUrl: './beverage-details.component.html',
  styleUrls: ['./beverage-details.component.css']
})
export class BeverageDetailsComponent implements OnInit {



  private originalBeverage: Beverage;
  private updateBeverage: Beverage;
  private beverageTypes;
  private facilityId: number;
  private facility: Facility;

  constructor(private route: ActivatedRoute,
              private beverageService: BeverageService,
              private beverageTypeService: BeverageTypeService,
              private id: IdService) {
    this.facilityId = this.id.getFacilityId();
    this.beverageTypes = this.beverageTypeService.getTypes();
    this.originalBeverage = new Beverage();
    this.updateBeverage = new Beverage();
    this.originalBeverage.type = '';
    this.facility = new Facility(this.facilityId);
  }

  private uiState = {
    showPage: true,
    view:{
      hiddenTable: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    form:{
      hiddenForm: true,
      hiddenLoadingGif: true,
      hiddenError: true,
      hiddenSuccess: true
    }
  };

  ngOnInit() {
    let id = this.route.snapshot.paramMap.get('id');
    if (!/^\d+$/.test(id) || id=='0') {
      this.uiState.showPage = false;
      console.log("OOOPS msg");
    }
    else{
      this.uiState.showPage = true;
      console.log("show");
      this.getBeverage(parseInt(id));
    }
  }

  getBeverage(id: number){
    this.beverageService
      .getBeverageById(id)
      .subscribe(
        res => {
          console.log("Success retrieving beverage");
          this.originalBeverage = res;
          console.log(this.originalBeverage);

          this.updateBeverage = new Beverage(
            this.originalBeverage.id,
            this.originalBeverage.name,
            this.originalBeverage.price,
            this.originalBeverage.description,
            this.originalBeverage.onPromotion,
            this.originalBeverage.facility,
            this.originalBeverage.type
          );
          this.updateBeverage.type = null;

          this.uiState.view = {
            hiddenTable: false,
            hiddenErrorMsg: true,
            hiddenLoadingGif: true
          };

          this.uiState.form.hiddenForm = false;
          this.uiState.form.hiddenLoadingGif = true;

        },
        err => {
          console.log("Error retrieving beverage");
          if(err.status == 200){
            this.uiState.showPage = false;
          }
          else {
            this.uiState.showPage = true;
          }
          this.uiState.view = {
            hiddenTable: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
          this.uiState.form.hiddenError = false;
        }
      )
  }

  updateBeverageSubmit(){
    this.uiState.form = {
      hiddenForm: false,
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenSuccess: true
    };
    this.updateBeverage.facility = this.facility;
    console.log(this.updateBeverage);
    this.beverageService
      .updateBeverage(this.updateBeverage)
      .subscribe(
        res => {
          console.log("Beverage successfully updated");
          this.originalBeverage = res;
          this.updateBeverage = new Beverage(
            this.originalBeverage.id,
            this.originalBeverage.name,
            this.originalBeverage.price,
            this.originalBeverage.description,
            this.originalBeverage.onPromotion,
            this.originalBeverage.facility,
            this.originalBeverage.type
          );
          this.updateBeverage.type = null;

          this.uiState.form = {
            hiddenForm: false,
            hiddenLoadingGif: true,
            hiddenError: true,
            hiddenSuccess: false
          };
        },
        err => {
          console.log("Error updating beverage");
          console.log(err.message);
          this.uiState.form = {
            hiddenForm: false,
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenSuccess: true
          };
        }
      )
  }

  getBeverageValue(key: string){
    return this.beverageTypeService.getBeverageValue(key);
  }

}
