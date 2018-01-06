import { Component, OnInit } from '@angular/core';
import {FacilityLocation} from "../../model/FacilityLocation";
import {Facility} from "../../model/Facility";
import {FacilityLocationService} from "../../service/http/facility-location/facility-location.service";
import {IdService} from "../../service/id-service/id.service";

@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrls: ['./add-location.component.css']
})
export class AddLocationComponent implements OnInit {

  private newLocation: FacilityLocation;
  private facilityId: number;
  private facility: Facility;
  private uiState = {
    hiddenLoadingGif: true,
    hiddenError: true,
    hiddenSuccess: true
  };
  constructor(private facilityLocationService: FacilityLocationService,
              private id: IdService) {
    this.newLocation = new FacilityLocation();
    this.facilityId = id.getFacilityId();
    this.facility = new Facility(this.facilityId);
  }

  ngOnInit() {
  }

  insertLocation(){
    this.uiState = {
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenSuccess: true
    };
    this.newLocation.facility = this.facility;
    this.facilityLocationService
      .insertFacilityLocation(this.newLocation)
      .subscribe(
        res => {
          console.log("Successfully added new location");
          this.uiState = {
            hiddenLoadingGif: true,
            hiddenError: true,
            hiddenSuccess: false
          };
        },
        err => {
          console.log("Error occured while adding new location");
          this.uiState = {
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenSuccess: true
          };
        }
      );
  }

  resetForm(){
    this.newLocation = new FacilityLocation();
  }

}
