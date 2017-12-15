import { Component, OnInit } from '@angular/core';
import {TaxAmount} from "../../model/TaxAmount";
import {TaxService} from "../../service/http/tax/tax.service";
import {DateParseService} from "../../service/date-parse/date-parse.service";

@Component({
  selector: 'app-tax',
  templateUrl: './tax.component.html',
  styleUrls: ['./tax.component.css']
})
export class TaxComponent implements OnInit {

  constructor(private taxService: TaxService,
              private dateParseService: DateParseService) { }

  public newTax: TaxAmount;
  public taxList: Array<TaxAmount>;
  public page: number = 1;
  public uiState = {
    newTax: {
      hiddenLoadingGif: true,
      hiddenError: true,
      hiddenSuccess: true
    },
    taxList: {
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    }
  };

  ngOnInit() {
    this.newTax = new TaxAmount();
    this.loadTaxes();
  }

  newTask(){
    console.log(this.newTax);
    this.uiState.newTax = { hiddenLoadingGif: false, hiddenError:true, hiddenSuccess:true };
    this.taxService
      .createTax(this.newTax)
      .subscribe(
        res => {
          console.log(res);
          this.uiState.newTax = { hiddenLoadingGif: true, hiddenError:true, hiddenSuccess:false };
          this.loadTaxes();
        },
        err => {
          this.uiState.newTax = { hiddenLoadingGif: true, hiddenError:false, hiddenSuccess:true };
          console.log("Error occurred");
        }
      );
  }

  loadTaxes(){
    this.uiState.taxList = {
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    };
    this.taxService
      .getAllTaxesSorted()
      .subscribe(
        res => {
          this.taxList = res;
          if(this.taxList.length > 0){
            this.uiState.taxList = {
              hiddenTable: false,
              hiddenNoDataInfo: true,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
          else{
            this.uiState.taxList = {
              hiddenTable: true,
              hiddenNoDataInfo: false,
              hiddenErrorMsg: true,
              hiddenLoadingGif: true
            };
          }
        },
        err => {
          this.uiState.taxList = {
            hiddenTable: true,
            hiddenNoDataInfo: true,
            hiddenErrorMsg: false,
            hiddenLoadingGif: true
          };
          console.log("Error occurred");
        }
      );
  }

  deleteTax(id: string){
    this.taxService
      .deleteTax(id)
      .subscribe(
        res => {
          console.log("Successfully deleted tax");
        },
        err => {
          console.log("Error occurred");
        }
      );
  }

  dateParsing(date){
    return this.dateParseService.dateParsing(date);
  }
}
