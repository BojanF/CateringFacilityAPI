import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SubscriptionPackage} from "../../../model/SubscriptionPackage";
import {LoginService} from "../login/login.service";

@Injectable()
export class PackageService {

  private packageUrl: string;

  constructor(private http: HttpClient,private loginService:LoginService) {
    this.packageUrl = 'http://localhost:8080/fe/package';
  }

  getPackageById(id: number){
    return this.http.get<SubscriptionPackage>(this.packageUrl + '/get-package/'+id,{headers:this.loginService.getCredentials()});
  }

  createPackage(subscriptionPackage: SubscriptionPackage){
    return this.http.post(this.packageUrl + '/new-package', subscriptionPackage,{headers:this.loginService.getCredentials()});
  }

  getActivePackages(){
    return this.http.get<Array<SubscriptionPackage>>(this.packageUrl + '/active-packages',{headers:this.loginService.getCredentials()});
  }

  packageStats(id: number){
    return this.http.get<Array<number>>(this.packageUrl + '/stats/' + id,{headers:this.loginService.getCredentials()});
  }

  packageIncomeStats(id: number){
    return this.http.get<Array<number>>(this.packageUrl + '/income-stats/' + id);
  }

  packagesStatusStats(){
    return this.http.get<Array<number>>(this.packageUrl + '/status-stats');
  }

  getAllPackages(){
    return this.http.get<Array<SubscriptionPackage>>(this.packageUrl + '/all-packages');
  }

  updatePackage(subscriptionPackage: SubscriptionPackage){
    return this.http.patch<SubscriptionPackage>(this.packageUrl + '/update-package/', subscriptionPackage);
  }

}
