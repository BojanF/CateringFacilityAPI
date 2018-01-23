import {BaseEntity} from "./BaseEntity";

export class SubscriptionPackageStats  extends BaseEntity{

  public name: string;

  public sumOfPaidInvoices: number;

  public sumOfUnpaidInvoices: number;

  public status: string;

}
