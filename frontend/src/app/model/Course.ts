import {MenuItem} from "./MenuItem";
import {Facility} from "./Facility";

export class Course extends MenuItem {
  constructor(id?: number, name?: string, price?: number, description?: string, onPromotion?: boolean, facility?: Facility, type?: string);

  constructor(id: number, name: string, price: number, description: string, onPromotion: boolean, facility: Facility, type: string) {
    super(id, name, price, description, onPromotion, facility);
    this.type = type;
  }

  public type: string;
}
