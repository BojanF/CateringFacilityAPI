export class BaseEntity{

  constructor(id?: number);

  constructor(id: number) {
    this.id = id;
  }
  public id: number;


}
