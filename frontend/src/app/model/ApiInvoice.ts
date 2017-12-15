import {Developer} from "./Developer";
import {SubscriptionPackage} from "./SubscriptionPackage";
import {Invoice} from "./Invoice";

export class ApiInvoice extends Invoice{
  public developer: Developer;
  getCreatedAt(){
    // this.createdAt;
    return 'Bojan';
  }
}
