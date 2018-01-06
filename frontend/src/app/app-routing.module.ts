import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdministratorComponent } from './administrator/administrator.component';
import { FacilityComponent } from './facility/facility.component';
import { LoginComponent } from './login/login.component';
import { AdminHomeComponent } from './administrator/admin-home/admin-home.component';
import { NewPackageComponent } from './administrator/new-package/new-package.component';
import { ViewPackagesComponent } from "./administrator/view-packages/view-packages.component";
import { PackageDetailsComponent } from "./administrator/package-details/package-details.component";
import {ApiInvoicesComponent} from "./administrator/api-invoices/api-invoices.component";
import {FacilityInvoicesComponent} from "./administrator/facility-invoices/facility-invoices.component";
import {TaxComponent} from "./administrator/tax/tax.component";
import {DeveloperComponent} from "./developer/developer.component";
import {DevHomeComponent} from "./developer/dev-home/dev-home.component";
import {DeveloperInvoicesComponent} from "./developer/developer-invoices/developer-invoices.component";
import {FacilityHomeComponent} from "./facility/facility-home/facility-home.component";
import {AddBeverageComponent} from "./facility/add-beverage/add-beverage.component";
import {AddCourseComponent} from "./facility/add-course/add-course.component";
import {ViewBeveragesComponent} from "./facility/view-beverages/view-beverages.component";
import {ViewCoursesComponent} from "./facility/view-courses/view-courses.component";
import {ViewInvoicesComponent} from "./facility/view-invoices/view-invoices.component";
import {BeverageDetailsComponent} from "./facility/beverage-details/beverage-details.component";
import {UpdateLocationComponent} from "./facility/update-location/update-location.component";
import {AddLocationComponent} from "./facility/add-location/add-location.component";
import {ViewLocationsComponent} from "./facility/view-locations/view-locations.component";
import {CourseDetailsComponent} from "./facility/course-details/course-details.component";

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'admin', redirectTo: 'admin', pathMatch: 'full'},
  {path: 'developer', redirectTo: 'developer', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'admin', component: AdministratorComponent,
   children:[
     {path: '', redirectTo: 'home', pathMatch: 'full'},
     {path: 'home', component: AdminHomeComponent},
     {path: 'new', component: NewPackageComponent},
     {path: 'packages', component: ViewPackagesComponent},
     {path: 'api-invoices', component: ApiInvoicesComponent},
     {path: 'facility-invoices', component: FacilityInvoicesComponent},
     {path: 'tax', component: TaxComponent},
     {path: 'package-details/:id', component: PackageDetailsComponent}
   ]
  },
  {path: 'developer', component: DeveloperComponent,
    children:[
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'home', component: DevHomeComponent},
      {path: 'invoices', component: DeveloperInvoicesComponent}
    ]
  },
  {
    path: 'facility', component: FacilityComponent,
      children: [
        {path: '', redirectTo: 'home', pathMatch: 'full'},
        {path: 'home', component: FacilityHomeComponent},
        {path: 'add-beverage', component: AddBeverageComponent},
        {path: 'add-course', component: AddCourseComponent},
        {path: 'beverages', component: ViewBeveragesComponent},
        {path: 'courses', component: ViewCoursesComponent},
        {path: 'invoices', component: ViewInvoicesComponent},
        {path: 'beverage/:id', component: BeverageDetailsComponent},
        {path: 'course/:id', component: CourseDetailsComponent},
        {path: 'add-location', component: AddLocationComponent},
        {path: 'locations', component: ViewLocationsComponent},
        {path: 'update-location/:id', component: UpdateLocationComponent}

      ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
