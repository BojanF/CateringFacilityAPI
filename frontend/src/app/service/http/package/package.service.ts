import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SubscriptionPackage} from "../../../model/SubscriptionPackage";

@Injectable()
export class PackageService {

  constructor(private http: HttpClient) { }

  getPackageById(id: string){
    return this.http.get<SubscriptionPackage>('http://localhost:8080/fe/package/get-package/'+id);
  }

  createPackage(subscriptionPackage: SubscriptionPackage){
    return this.http.post('http://localhost:8080/fe/package/new-package', subscriptionPackage);
  }

  getAllPackages(){
    return this.http.get<Array<SubscriptionPackage>>('http://localhost:8080/fe/package/all-packages');
  }

  updatePackage(subscriptionPackage: SubscriptionPackage){
    return this.http.patch<SubscriptionPackage>('http://localhost:8080/fe/package/update-package/', subscriptionPackage);
  }

}
