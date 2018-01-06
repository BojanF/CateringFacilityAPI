import { Component, OnInit } from '@angular/core';
import {FacilityService} from "../../service/http/facility/facility.service";
import {IdService} from "../../service/id-service/id.service";
import {FacilityLocation} from "../../model/FacilityLocation";
import {FacilityLocationService} from "../../service/http/facility-location/facility-location.service";

@Component({
  selector: 'app-view-locations',
  templateUrl: './view-locations.component.html',
  styleUrls: ['./view-locations.component.css']
})
export class ViewLocationsComponent implements OnInit {

  private facilityId: number;
  private facilityLocations: Array<FacilityLocation>;
  private page = 1;
  private uiState = {
    locationsList:{
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    deleteLocation: {
      hiddenDeletingGif: true,
      // hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    }
  };
  constructor(private facilityService: FacilityService,
              private facilityLocationService: FacilityLocationService,
              private id: IdService) {
    this.facilityId = id.getFacilityId();

  }

  ngOnInit() {
    this.getFacilityLocations();
  }

  getFacilityLocations(){
    this.uiState.locationsList.hiddenLoadingGif = false;
    // this.uiState.locationsList = {
    //   hiddenTable: true,
    //   hiddenNoDataInfo: true,
    //   hiddenErrorMsg: true,
    //   hiddenLoadingGif: false
    // };
    this.facilityService
      .facilityLocations(this.facilityId)
      .subscribe(
        res => {
          console.log("Locations fetched");
          this.facilityLocations = res;
          if(this.facilityLocations.length > 0) {
            this.uiState.locationsList = {
              hiddenTable: false,
              hiddenNoDataInfo: true,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
          else {
            this.uiState.locationsList = {
              hiddenTable: true,
              hiddenNoDataInfo: false,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
        },
        err => {
          console.log("Error fetching locations");
          this.uiState.locationsList = {
            hiddenTable: true,
            hiddenNoDataInfo: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
        }
      );
  }


  deleteLocation(id: number){
    this.uiState.deleteLocation.hiddenDeletingGif = false;
    this.facilityLocationService
      .deleteLocation(id)
      .subscribe(
        res => {
          console.log("Successfully deleted location");
          this.uiState.deleteLocation = {
            hiddenDeletingGif: true,
            // hiddenSuccessMsg: false,
            hiddenErrorMsg: true
          };
          this.getFacilityLocations();
        },
        err => {
          console.log("Error deleting location");
          this.uiState.deleteLocation = {
            hiddenDeletingGif: true,
            // hiddenSuccessMsg: true,
            hiddenErrorMsg: false
          };
          console.log(err.message);
        }
      );
  }

}
