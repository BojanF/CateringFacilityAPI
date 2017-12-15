import { Component, OnInit } from '@angular/core';
import {FacilityInvoice} from "../../model/FacilityInvoice";
import {FacilityInvoiceService} from "../../service/http/facility-invoice/facility-invoice.service";
import {DateParseService} from "../../service/date-parse/date-parse.service";

@Component({
  selector: 'app-facility-invoices',
  templateUrl: './facility-invoices.component.html',
  styleUrls: ['./facility-invoices.component.css']
})
export class FacilityInvoicesComponent implements OnInit {

  constructor(private facilityInvoiceService: FacilityInvoiceService,
              private dateParseService: DateParseService) { }
  public facilityInvoices: Array<FacilityInvoice>;
  public page: number = 1;

  uiState = {
    hiddenTable: true,
    hiddenNoDataInfo: true,
    hiddenErrorMsg: true,
    hiddenLoadingGif: false
  };

  ngOnInit() {
    this.facilityInvoiceService
      .getAllFacilityInvoicesSorted()
      .subscribe(
        res => {
          console.log(res);
          this.facilityInvoices = res;
          if(this.facilityInvoices.length > 0){
            this.uiState = {
              hiddenTable: false,
              hiddenNoDataInfo: true,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
          else{
            this.uiState = {
              hiddenTable: true,
              hiddenNoDataInfo: false,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
        },
        err => {
          console.log("Error occurred");
          this.uiState = {
            hiddenTable: true,
            hiddenNoDataInfo: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
        }
      );
  }

  dateParsing(date){
    return this.dateParseService.dateParsing(date);
  }

}
