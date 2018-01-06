import {Facility} from "./Facility";
import {BaseEntity} from "./BaseEntity";

export class FacilityLocation  extends BaseEntity{

  constructor(id?: number, name?: string, city?: string, address?: string, facility?: Facility);

  constructor(id: number, name: string, city: string, address: string, facility: Facility) {
    super(id);
    this.name = name;
    this.city = city;
    this.address = address;
    this.facility = facility;
  }

  public name: string;
  public city: string;
  public address: string;
  public facility: Facility;
}
