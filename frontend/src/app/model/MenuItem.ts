import {Facility} from "./Facility";
import {BaseEntity} from "./BaseEntity";

export class MenuItem extends BaseEntity{
  constructor(id: number, name: string, price: number, description: string, onPromotion: boolean, facility: Facility) {
    super(id);
    this.name = name;
    this.price = price;
    this.description = description;
    this.onPromotion = onPromotion;
    this.facility = facility;
  }

  public name: string;
  public price: number;
  public description: string;
  public onPromotion: boolean;
  public facility: Facility;
}
