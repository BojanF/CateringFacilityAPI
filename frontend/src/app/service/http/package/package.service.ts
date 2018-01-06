import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SubscriptionPackage} from "../../../model/SubscriptionPackage";

@Injectable()
export class PackageService {

  private packageUrl: string;

  constructor(private http: HttpClient) {
    this.packageUrl = 'http://localhost:8080/fe/package';
  }

  getPackageById(id: number){
    return this.http.get<SubscriptionPackage>(this.packageUrl + '/get-package/'+id);
  }

  createPackage(subscriptionPackage: SubscriptionPackage){
    return this.http.post(this.packageUrl + '/new-package', subscriptionPackage);
  }

  getAllPackages(){
    return this.http.get<Array<SubscriptionPackage>>(this.packageUrl + '/all-packages');
  }

  updatePackage(subscriptionPackage: SubscriptionPackage){
    return this.http.patch<SubscriptionPackage>(this.packageUrl + '/update-package/', subscriptionPackage);
  }

}
