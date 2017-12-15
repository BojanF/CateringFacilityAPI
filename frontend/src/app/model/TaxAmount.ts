import {BaseEntity} from "./BaseEntity";

export class TaxAmount extends BaseEntity{
  constructor(id?: number, activeSince?: Date, amount?: number);
  constructor(id: number, activeSince: Date, amount: number) {
    super(id);
    this.activeSince = activeSince;
    this.amount = amount;
  }

  public activeSince: Date;
  public amount: number;
}
