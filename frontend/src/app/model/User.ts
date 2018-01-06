import {BaseEntity} from "./BaseEntity";

export class User extends BaseEntity{
  constructor(id?: number, username?: string, email?: string, password?: string, role?: string);
  constructor(id: number, username: string, email: string, password: string, role: string) {
    super(id);
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
  }
  public username: string;
  public email: string;
  public password: string;
  public role: string;
}
