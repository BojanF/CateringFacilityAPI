import {Component, OnInit} from '@angular/core';
import {ApiInvoice} from "../../model/ApiInvoice";
import {ApiInvoiceService} from "../../service/http/api-invoice/api-invoice.service";
import {DateParseService} from "../../service/date-parse/date-parse.service";

@Component({
  selector: 'app-api-invoices',
  templateUrl: './api-invoices.component.html',
  styleUrls: ['./api-invoices.component.css']
})
export class ApiInvoicesComponent implements OnInit {

  constructor(private apiInvoiceService: ApiInvoiceService,
              private dateParseService: DateParseService) { }
  public apiInvoices: Array<ApiInvoice>;
  public page: number = 1;

  private uiState = {
    hiddenTable: true,
    hiddenNoDataInfo: true,
    hiddenErrorMsg: true,
    hiddenLoadingGif: false
  };

  ngOnInit() {
    this.apiInvoiceService
      .getAllApiInvoicesSorted()
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
