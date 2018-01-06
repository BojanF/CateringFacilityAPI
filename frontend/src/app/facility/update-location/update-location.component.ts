import { Component, OnInit } from '@angular/core';
import {FacilityLocation} from "../../model/FacilityLocation";
import {FacilityLocationService} from "../../service/http/facility-location/facility-location.service";
import {IdService} from "../../service/id-service/id.service";
import {ActivatedRoute} from "@angular/router";
import {Facility} from "../../model/Facility";
import {FacilityLocationContact} from "../../model/FacilityLocationContact";
import {FacilityLocationContactService} from "../../service/http/facility-location-contact/facility-location-contact.service";
import {LocationContactId} from "../../model/LocationContactId";


@Component({
  selector: 'app-update-location',
  templateUrl: './update-location.component.html',
  styleUrls: ['./update-location.component.css']
})
export class UpdateLocationComponent implements OnInit {

  private updateLocation: FacilityLocation;
  private facLocationContacts: Array<FacilityLocationContact>;
  private locationName: string;
  private facilityId: number;
  private facilityLocationId: number;
  private facility: Facility;
  private contact: FacilityLocationContact;
  private newContactNumber: string;
  private page: number = 1;
  constructor(private route: ActivatedRoute,
              private facilityLocationService: FacilityLocationService,
              private contactService: FacilityLocationContactService,
              private id: IdService) {
    this.facilityId = id.getFacilityId();
    this.facility = new Facility(this.facilityId);
    this.updateLocation = new FacilityLocation();
  }

  private uiState = {
    showPage: true,
    newContact: {
      hiddenLoadingGif: true,
      hiddenErrorMsg: true,
      hiddenSuccessMsg: true
    },
    update:{
      hiddenForm: true,
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenSuccess: true
    },
    contactList: {
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    deleteContact: {
      hiddenDeletingGif: true,
      // hiddenSuccessMsg: true,
      hiddenErrorMsg: true
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
      this.facilityLocationId = parseInt(id);
      this.getFacilityLocation(this.facilityLocationId);
      this.loadContactsForFacilityLocation();
    }
  }

  getFacilityLocation(id: number){
    this.facilityLocationService
      .getLocationById(id)
      .subscribe(
        res => {
          console.log("Facility location fetched");
          this.updateLocation = res;
          this.locationName = this.updateLocation.name;
          this.uiState.update = {
            hiddenForm: false,
            hiddenLoadingGif: true,
            hiddenError: true,
            hiddenSuccess: true
          };
        },
        err => {
          console.log("Facility location fetching ERROR");
          this.uiState.update = {
            hiddenForm: true,
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenSuccess: true
          };
          if(err.status == 200){
            this.uiState.showPage = false;
          }
          else{
            this.uiState.showPage = true;
          }
        }
      );

  }

  updateLocationSubmit(){
    this.uiState.update = {
      hiddenForm: false,
      hiddenLoadingGif: false,
      hiddenError: true,
      hiddenSuccess: true
    };
    this.updateLocation.facility = this.facility;
    this.facilityLocationService
      .updateLocation(this.updateLocation)
      .subscribe(
        res => {
          console.log("Successfully updated");
          this.updateLocation = res;
          this.locationName = this.updateLocation.name;
          this.uiState.update = {
            hiddenForm: false,
            hiddenLoadingGif: true,
            hiddenError: true,
            hiddenSuccess: false
          };
        },
        err => {
          console.log("Updating error");
          this.uiState.update = {
            hiddenForm: true,
            hiddenLoadingGif: true,
            hiddenError: false,
            hiddenSuccess: false
          };
        }
      )
  }

  loadContactsForFacilityLocation(){
    this.uiState.contactList.hiddenLoadingGif = false;
    this.uiState.deleteContact = {
      hiddenDeletingGif: true,
      // hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    };
    this.facilityLocationService
      .facilityLocationContacts(this.facilityLocationId)
      .subscribe(
        res => {
          console.log("Successfully fetched contacts");
          this.facLocationContacts = res;
          if(this.facLocationContacts.length > 0){
            this.uiState.contactList = {
              hiddenTable: false,
              hiddenNoDataInfo: true,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
          else{
            this.uiState.contactList = {
              hiddenTable: true,
              hiddenNoDataInfo: false,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
        },
        err => {
          console.log("Error fetching contacts");
          this.uiState.contactList = {
            hiddenTable: true,
            hiddenNoDataInfo: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
        }
      );
  }

  addContact(){
    this.uiState.newContact = {
      hiddenLoadingGif: false,
      hiddenErrorMsg:true,
      hiddenSuccessMsg:true
    };
    this.contact = new FacilityLocationContact();
    this.contact.id = new LocationContactId(this.facilityLocationId, this.newContactNumber);
    this.contactService
      .insertLocationContact(this.contact)
      .subscribe(
        res => {
          console.log("New contact added");
          this.uiState.newContact = {
            hiddenLoadingGif: true,
            hiddenErrorMsg:true,
            hiddenSuccessMsg:false
          };
          this.loadContactsForFacilityLocation();
        },
        err => {
          console.log("Error adding new contact");
          this.uiState.newContact = {
            hiddenLoadingGif: true,
            hiddenErrorMsg:false,
            hiddenSuccessMsg:true
          };
        }
      );
  }

  deleteFacilityLocationContact(locationId: number, telNumber: string){
    this.uiState.deleteContact = {
      hiddenDeletingGif: false,
      // hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    };
    this.contactService
      .deleteContact(locationId, telNumber)
      .subscribe(
        res => {
          console.log("Successfully deleted contact");
          this.uiState.deleteContact = {
            hiddenDeletingGif: true,
            // hiddenSuccessMsg: false,
            hiddenErrorMsg: true
          };
          this.loadContactsForFacilityLocation();
        },
        err => {
          console.log("Error occurred deleting contact");
          this.uiState.deleteContact = {
            hiddenDeletingGif: true,
            // hiddenSuccessMsg: true,
            hiddenErrorMsg: false
          };
          console.log(err.message);
        }
      );

  }
}
