import {BaseEntity} from "./BaseEntity";

export class SubscriptionPackage extends BaseEntity{
  constructor(id?: number, name?: string, price?: number, expiresIn?: number, status?: string, description?: string);

  constructor(id: number, name: string, price: number, expiresIn: number, status: string, description: string) {
    super(id);
    this.name = name;
    this.price = price;
    this.expiresIn = expiresIn;
    this.status = status;
    this.description = description;
  }

  public name: string;
  public price: number;
  public expiresIn: number;
  public status: string;
  public description: string;

  // copyConstructor(/*subscriptionPackage: SubscriptionPackage*/): SubscriptionPackage{
  //    let newOne = new SubscriptionPackage();
  //    newOne.name = this.name;
  //    newOne.price = this.price;
  //    newOne.expiresIn = this.expiresIn;
  //    newOne.status = this.status;
  //    newOne.description = this.description;
  //    return newOne;
  // }

}
