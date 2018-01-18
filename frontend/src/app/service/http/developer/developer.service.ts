import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class DeveloperService {

  private developerUrl: string;

  constructor(private http: HttpClient,) {
    this.developerUrl = 'http://localhost:8080/fe/developer';
  }

  allowSubscription(developerId: number){
    return this.http.get(this.developerUrl + '/allow-subscription/' + developerId);
  }

  developerInvoicesStats(developerId: number){
    return this.http.get<Array<number>>(this.developerUrl + '/invoices-stats/' + developerId);
  }

}
