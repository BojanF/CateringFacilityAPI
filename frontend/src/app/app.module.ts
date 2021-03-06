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
import { DevHomeComponent } from './developer/dev-home/dev-home.component';
import { DeveloperInvoicesComponent } from './developer/developer-invoices/developer-invoices.component';
import { FacilityHomeComponent } from './facility/facility-home/facility-home.component';
import { AddBeverageComponent } from './facility/add-beverage/add-beverage.component';
import {BeverageService} from "./service/http/beverage/beverage.service";
import {FacilityService} from "./service/http/facility/facility.service";
import {CourseService} from "./service/http/course/course.service";
import { AddCourseComponent } from './facility/add-course/add-course.component';
import { ViewBeveragesComponent } from './facility/view-beverages/view-beverages.component';
import { ViewCoursesComponent } from './facility/view-courses/view-courses.component';
import { ViewInvoicesComponent } from './facility/view-invoices/view-invoices.component';
import { BeverageDetailsComponent } from './facility/beverage-details/beverage-details.component';
import { CourseDetailsComponent } from './facility/course-details/course-details.component';
import {BeverageTypeService} from "./service/beverage-type/beverage-type.service";
import {IdService} from "./service/id-service/id.service";
import {FacilityLocationService} from "./service/http/facility-location/facility-location.service";
import { AddLocationComponent } from './facility/add-location/add-location.component';
import { ViewLocationsComponent } from './facility/view-locations/view-locations.component';
import { UpdateLocationComponent } from './facility/update-location/update-location.component';
import {CourseTypeService} from "./service/course-type/course-type.service";
import {FacilityLocationContactService} from "./service/http/facility-location-contact/facility-location-contact.service";
import { FacGetSubscriptionComponent } from './facility/fac-get-subscription/fac-get-subscription.component';
import { DevGetSubscriptionComponent } from './developer/dev-get-subscription/dev-get-subscription.component';
import { DevViewActivePackagesComponent } from './developer/dev-view-active-packages/dev-view-active-packages.component';
import { FacViewActivePackagesComponent } from './facility/fac-view-active-packages/fac-view-active-packages.component';
import {AdministratorService} from "./service/http/administrator/administrator.service";
import {DeveloperService} from "./service/http/developer/developer.service";
import {LoginService} from "./service/http/login/login.service";
import {PackagesStatsComponent} from "./administrator/packages-stats/packages-stats.component";
import {PayPalService} from "./service/http/paypal/paypal.service";
import {PayPalComponent} from "./paypal/paypal.component";


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
    TaxComponent,
    DevHomeComponent,
    DeveloperInvoicesComponent,
    FacilityHomeComponent,
    AddBeverageComponent,
    AddCourseComponent,
    ViewBeveragesComponent,
    ViewCoursesComponent,
    ViewInvoicesComponent,
    BeverageDetailsComponent,
    CourseDetailsComponent,
    AddLocationComponent,
    ViewLocationsComponent,
    UpdateLocationComponent,
    FacGetSubscriptionComponent,
    DevGetSubscriptionComponent,
    DevViewActivePackagesComponent,
    FacViewActivePackagesComponent,
    PackagesStatsComponent,
    PayPalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxPaginationModule
  ],
  providers: [
    IdService, //temp

    DateParseService,
    PackageService,
    TaxService,
    ApiInvoiceService,
    FacilityInvoiceService,
    CourseService,
    BeverageService,
    FacilityService,
    BeverageTypeService,
    CourseTypeService,
    FacilityLocationService,
    FacilityLocationContactService,
    AdministratorService,
    DeveloperService,
    LoginService,
    PayPalService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
