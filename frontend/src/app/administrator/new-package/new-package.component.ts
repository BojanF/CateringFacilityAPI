import { Component, OnInit } from '@angular/core';
import { SubscriptionPackage } from '../../model/SubscriptionPackage'
import {PackageService} from "../../service/http/package/package.service";

@Component({
  selector: 'app-new-package',
  templateUrl: './new-package.component.html',
  styleUrls: ['./new-package.component.css']
})
export class NewPackageComponent implements OnInit {

  private uiState = {
    hiddenLoadingGif: true,
    hiddenError: true,
    hiddenSuccess: true
  };

  protected package: SubscriptionPackage;

  constructor(private packageService: PackageService) {
    this.package = new SubscriptionPackage();
  }

  ngOnInit() {
  }

  resetForm(){
    this.package = new SubscriptionPackage();
    this.uiState = { hiddenLoadingGif: true,
                     hiddenError:true,
                     hiddenSuccess:true };
  }

  onSubmit(){
    this.uiState = { hiddenLoadingGif: false,
                     hiddenError:true,
                     hiddenSuccess:true };
    this.packageService
      .createPackage(this.package)
      .subscribe(
        res => {
          console.log("Success");
          this.uiState = { hiddenLoadingGif: true,
                           hiddenError:true,
                           hiddenSuccess:false };
        },
        err => {
          this.uiState = { hiddenLoadingGif: true, hiddenError:false, hiddenSuccess:true };
          console.log("Error occurred");
        }
      );
  }

}
