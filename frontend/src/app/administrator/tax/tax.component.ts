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
      hiddenErrorMsg: true,
      hiddenSuccessMsg: true
    },
    taxList: {
      hiddenTable: true,
      hiddenNoDataInfo: true,
      hiddenErrorMsg: true,
      hiddenLoadingGif: false
    },
    deleteTax: {
      hiddenDeletingGif: true,
      hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    }
  };

  ngOnInit() {
    this.newTax = new TaxAmount();
    this.loadTaxes();
  }

  newTask(){
    this.uiState.newTax = {
      hiddenLoadingGif: false,
      hiddenErrorMsg:true,
      hiddenSuccessMsg:true
    };

    this.uiState.deleteTax = {
      hiddenDeletingGif: true,
      hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    };

    this.taxService
      .createTax(this.newTax)
      .subscribe(
        res => {
          console.log("Successfully created tax!");
          this.uiState.newTax = {
            hiddenLoadingGif: true,
            hiddenErrorMsg:true,
            hiddenSuccessMsg:false
          };
          this.loadTaxes();
        },
        err => {
          this.uiState.newTax = {
            hiddenLoadingGif: true,
            hiddenErrorMsg:false,
            hiddenSuccessMsg:true
          };
          console.log("Error occurred creating tax");
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

    this.uiState.deleteTax = {
      hiddenDeletingGif: true,
      hiddenSuccessMsg: true,
      hiddenErrorMsg: true
    };

    this.taxService
      .getAllTaxesSorted()
      .subscribe(
        res => {
          console.log("Taxes successfully loaded");
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
          console.log("Error occurred loading taxes");
        }
      );
  }

  deleteTax(id: number){
    this.uiState.deleteTax.hiddenDeletingGif = false;
    this.taxService
      .deleteTax(id)
      .subscribe(
        res => {
          console.log("Successfully deleted tax");
          this.uiState.deleteTax = {
            hiddenDeletingGif: true,
            hiddenSuccessMsg: false,
            hiddenErrorMsg: true
          };
          this.loadTaxes();
        },
        err => {
          console.log("Error occurred REAL");
          this.uiState.deleteTax = {
            hiddenDeletingGif: true,
            hiddenSuccessMsg: true,
            hiddenErrorMsg: false
          };
          console.log(err.message);
        }
      );
  }

  dateParsing(date){
    return this.dateParseService.dateParsing(date);
  }

  validateTax(){
    if(this.newTax.amount>=0 && this.newTax.amount<=100){
      return true;
    }
    else{
      this.newTax.amount = null;
      return false;
    }
  }
}
