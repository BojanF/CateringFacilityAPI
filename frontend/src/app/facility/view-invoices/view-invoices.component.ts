import { Component, OnInit } from '@angular/core';
import {FacilityService} from "../../service/http/facility/facility.service";
import {FacilityInvoice} from "../../model/FacilityInvoice";
import {DateParseService} from "../../service/date-parse/date-parse.service";
import {IdService} from "../../service/id-service/id.service";

@Component({
  selector: 'app-view-invoices',
  templateUrl: './view-invoices.component.html',
  styleUrls: ['./view-invoices.component.css']
})
export class ViewInvoicesComponent implements OnInit {

  constructor(private facilityService: FacilityService,
              private dateParseService: DateParseService,
              private id: IdService) {

    this.facilityId = this.id.getFacilityId();

  }

  private facilityId: number;
  protected facilityInvoices: Array<FacilityInvoice>;
  private uiState = {
    invoiceList:{
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    }
  };
  public page: number = 1;

  ngOnInit() {
    this.getFacilityInvoices();
  }

  getFacilityInvoices(){
    this.uiState.invoiceList.hiddenLoadingGif = false;
    this.facilityService
      .facilityInvoices(this.facilityId)
      .subscribe(
        res => {
          console.log("Successfully fetched invoices");
          this.facilityInvoices = res;
          if(this.facilityInvoices.length > 0){
            this.uiState.invoiceList = {
              hiddenTable: false,
              hiddenNoDataInfo: true,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
          else{
            this.uiState.invoiceList = {
              hiddenTable: true,
              hiddenNoDataInfo: false,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
        },
        err => {
          console.log("Error fetching invoices!");
          this.uiState.invoiceList = {
            hiddenTable: true,
            hiddenNoDataInfo: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
        }
      )
  }

  dateParsing(date){
    return this.dateParseService.dateParsing(date);
  }
}
