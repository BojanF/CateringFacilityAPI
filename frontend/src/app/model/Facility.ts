import {Customer} from "./Customer";

export class Facility extends Customer{
  constructor(id?: number, username?: string, email?: string, password?: string, role?: string, status?: string, usedTrial?: boolean, name?: string);
  constructor(id: number, username: string, email: string, password: string, role: string, status: string, usedTrial: boolean, name: string) {
    super(id, username, email, password, role, status, usedTrial);
    this.name = name;
  }
  public name: string;
}
