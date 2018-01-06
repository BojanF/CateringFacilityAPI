import {User} from "./User";

export class Customer extends User{
  constructor(id?: number, username?: string, email?: string, password?: string, role?: string, status?: string, usedTrial?: boolean);
  constructor(id: number, username: string, email: string, password: string, role: string, status: string, usedTrial: boolean) {
    super(id, username, email, password, role);
    this.status = status;
    this.usedTrial = usedTrial;
  }
  public status: string;
  public usedTrial: boolean;
}
