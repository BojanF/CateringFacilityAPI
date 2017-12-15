import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule}   from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {AdministratorComponent} from './administrator/administrator.component';
import {AppRoutingModule} from './app-routing.module';
import {FacilityComponent} from './facility/facility.component';
import {DeveloperComponent} from './developer/developer.component';
import {LoginComponent} from './login/login.component';
import {NewPackageComponent} from './administrator/new-package/new-package.component';
import {AdminHomeComponent} from './administrator/admin-home/admin-home.component';
import {ViewPackagesComponent} from './administrator/view-packages/view-packages.component';
import {PackageDetailsComponent} from './administrator/package-details/package-details.component';
import {NgxPaginationModule} from 'ngx-pagination';
import {ApiInvoicesComponent} from './administrator/api-invoices/api-invoices.component';
import {FacilityInvoicesComponent} from './administrator/facility-invoices/facility-invoices.component';
import {TaxComponent} from './administrator/tax/tax.component';
import {PackageService} from "./service/http/package/package.service";
import {TaxService} from "./service/http/tax/tax.service";
import {ApiInvoiceService} from "./service/http/api-invoice/api-invoice.service";
import {FacilityInvoiceService} from "./service/http/facility-invoice/facility-invoice.service";
import {DateParseService} from "./service/date-parse/date-parse.service";
// import {NgxSelectOptions} from "ngx-select-options";

@NgModule({
  declarations: [
    AppComponent,
    AdministratorComponent,
    FacilityComponent,
    DeveloperComponent,
    LoginComponent,
    NewPackageComponent,
    AdminHomeComponent,
    ViewPackagesComponent,
    PackageDetailsComponent,
    ApiInvoicesComponent,
    FacilityInvoicesComponent,
    TaxComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxPaginationModule
  ],
  providers: [
    DateParseService,
    PackageService,
    TaxService,
    ApiInvoiceService,
    FacilityInvoiceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
