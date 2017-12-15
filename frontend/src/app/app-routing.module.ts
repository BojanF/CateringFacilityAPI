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

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'admin', redirectTo: 'admin', pathMatch: 'full'},
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
  // {path: 'admin/home', component: AdminHomeComponent},

  {path: 'facility', component: FacilityComponent}
  // {path: 'edit/:title', component: VideoEditComponent},
  // {path: 'edit/', component: VideoEditComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
