import {BaseEntity} from "./BaseEntity";

export class User extends BaseEntity{
  public username: string;
  public email: string;
  public password: string;
  public role: string;
}
