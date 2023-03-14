import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {CompaniesComponent} from "./components/companies/companies.component";
import {CompanyDetailComponent} from './components/company-detail/company-detail.component';
import {SharedModule} from "../component/shared.module";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from "@angular/material/list";
import {TranslateModule} from "@ngx-translate/core";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";

const routes: Routes = [
  {path: '', component: CompaniesComponent},
  {path: ':id', component: CompanyDetailComponent},
];

@NgModule({
  declarations: [CompaniesComponent, CompanyDetailComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule,
    MatCardModule,
    TranslateModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
  ]
})
export class CompaniesModule {
}
