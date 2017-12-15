import {BaseEntity} from "./BaseEntity";
import {SubscriptionPackage} from "./SubscriptionPackage";

export class Invoice extends BaseEntity{
  public subscribe: SubscriptionPackage;
  public originalPackagePrice: number;
  public grossPrice: number;
  public taxAmount:number ;
  public createdAt: Date;
  public payedAt: Date;
  public invoicePayed: boolean;
}
