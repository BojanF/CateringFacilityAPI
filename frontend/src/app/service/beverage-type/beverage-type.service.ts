import { Injectable } from '@angular/core';

@Injectable()
export class BeverageTypeService {

  private beverageTypes;

  constructor() {
    this.beverageTypes = [
      {val:'Coffee', key:'COFFEE'},
      {val:'Juice', key:'JUICE'},
      {val:'Vine', key:'VINE'},
      {val:'Alcohol', key:'ALCOHOL'},
      {val:'Cocktail', key:'COCKTAIL'},
      {val:'Beer', key:'BEER'},
      {val:'Tea', key:'TEA'},
      {val:'Ice tea', key:'ICE_TEA'}];
  }

  getTypes(){
    return this.beverageTypes;
  }

  getBeverageValue(key: string){
    if(key == ''){
      return null;
    }
    else {
      return this.beverageTypes.filter(beverage => beverage.key == key).pop().val;
    }

  }
}
