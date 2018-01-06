import { Component, OnInit } from '@angular/core';
import {Beverage} from "../../model/Beverage";
import {BeverageService} from "../../service/http/beverage/beverage.service";
import {FacilityService} from "../../service/http/facility/facility.service";
import {Facility} from "../../model/Facility";
import {BeverageTypeService} from "../../service/beverage-type/beverage-type.service";
import {IdService} from "../../service/id-service/id.service";

@Component({
  selector: 'app-add-beverage',
  templateUrl: './add-beverage.component.html',
  styleUrls: ['./add-beverage.component.css']
})
export class AddBeverageComponent implements OnInit {

  protected newBeverage: Beverage;
  private beverageTypes;
  private facilityId: number;
  private facility: Facility;
  public uiState = {
    hiddenLoadingGif: true,
    hiddenError: true,
    hiddenSuccess: true
  };

  constructor(private beverageService: BeverageService,
              private beverageTypeService: BeverageTypeService,
              private id: IdService) {

    this.beverageTypes = this.beverageTypeService.getTypes();
    this.facilityId = this.id.getFacilityId();

    this.newBeverage = new Beverage();
    this.facility = new Facility(this.facilityId);
  }

  ngOnInit() {

  }

  onSubmit(){
    // this.getFacility();
    // this.insertBeverage();
  }

  // getFacility(){
  //   this.uiState.hiddenLoadingGif = false;
  //   this.facilityService
  //     .getFacilityById(this.facilityId)
  //     .subscribe(
  //       res => {
  //         console.log("Facility fetched");
  //         this.facility = res;
  //         this.insertBeverage();
  //       },
  //       err => {
  //         console.log("Fetch facility error");
  //         this.uiState = {
  //           hiddenLoadingGif: true,
  //           hiddenError: false,
  //           hiddenSuccess: true
  //         };
  //       }
  //     );
  // }

  insertBeverage(){
    this.uiState = {
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenSuccess: true
    };
    this.newBeverage.facility = this.facility;
    this.beverageService
      .insertBeverage(this.newBeverage)
      .subscribe(
        res => {
          console.log("New beverage successfully added");
          this.uiState = {
            hiddenLoadingGif: true,
            hiddenError: true,
            hiddenSuccess: false
          };
        },
        err => {
          console.log("Error adding new Beverage");
          console.log(err.message);
          this.uiState = {
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenSuccess: true
          };
        }
      )

  }

  resetForm(){
    this.newBeverage = new Beverage();
  }



}
