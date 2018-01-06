import { Component, OnInit } from '@angular/core';
import {DateParseService} from "../../service/date-parse/date-parse.service";
import {ApiInvoice} from "../../model/ApiInvoice";
import {ApiInvoiceService} from "../../service/http/api-invoice/api-invoice.service";
import {IdService} from "../../service/id-service/id.service";



@Component({
  selector: 'app-developer-invoices',
  templateUrl: './developer-invoices.component.html',
  styleUrls: ['./developer-invoices.component.css']
})
export class DeveloperInvoicesComponent implements OnInit {

  private developerID: number;
  private apiInvoices: Array<ApiInvoice>;
  private page: number = 1;

  private uiState = {
    hiddenTable: true,
    hiddenNoDataInfo: true,
    hiddenErrorMsg: true,
    hiddenLoadingGif: false
  };
  constructor(private apiInvoiceService: ApiInvoiceService,
              private dateParseService: DateParseService,
              private id: IdService) {
    this.developerID = this.id.getDeveloperId();
  }




  ngOnInit() {
    this.apiInvoiceService
      .getAllApiInvoicesForDeveloper(this.developerID)
      .subscribe(
        res => {
          this.apiInvoices = res;
          if(this.apiInvoices.length > 0){
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
          console.log(this.apiInvoices);
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
      )
  }

  dateParsing(date){
    return this.dateParseService.dateParsing(date);
  }

}
