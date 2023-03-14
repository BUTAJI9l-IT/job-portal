import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {UserComponent} from "./components/user/user.component";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {UserAccountComponent} from './components/user-account/user-account.component';
import {UserSettingsComponent} from './components/user-settings/user-settings.component';
import {ApplicantApplicationsComponent} from './components/applicant-applications/applicant-applications.component';
import {ApplicantSavedJobsComponent} from './components/applicant-saved-jobs/applicant-saved-jobs.component';
import {CompanyJobPositionsComponent} from './components/company-job-positions/company-job-positions.component';
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {MatLineModule, MatRippleModule} from "@angular/material/core";
import {TranslateModule} from "@ngx-translate/core";
import {MatButtonModule} from "@angular/material/button";
import {FlexLayoutModule} from "@angular/flex-layout";
import {UserAccountCompanyComponent} from './components/user-account-company/user-account-company.component';
import {SharedModule} from "../component/shared.module";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatMenuModule} from "@angular/material/menu";
import {AvatarMenuComponent} from './components/avatar-menu/avatar-menu.component';
import {AbstractAccountComponent} from './components/abstract/abstract-account/abstract-account.component';
import {UserAccountApplicantComponent} from './components/user-account-applicant/user-account-applicant.component';
import {MatSelectModule} from "@angular/material/select";
import {QuillModule} from "ngx-quill";
import {MatChipsModule} from "@angular/material/chips";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {UserAccountAdminComponent} from './components/user-account-admin/user-account-admin.component';


const routes: Routes = [
  {path: ':id', redirectTo: ':id/account', pathMatch: "full"},
  {path: ':id/account', component: UserAccountComponent},
  {path: ':id/settings', component: UserSettingsComponent},
  {path: ':id/applications', component: ApplicantApplicationsComponent},
  {path: ':id/saved-jobs', component: ApplicantSavedJobsComponent},
  {path: ':id/jobs', component: CompanyJobPositionsComponent},
];

@NgModule({
  declarations: [UserComponent, UserAccountComponent, UserSettingsComponent, ApplicantApplicationsComponent, ApplicantSavedJobsComponent, CompanyJobPositionsComponent, UserAccountCompanyComponent, AvatarMenuComponent, AbstractAccountComponent, UserAccountApplicantComponent, UserAccountAdminComponent],
  imports: [
    SharedModule,
    CommonModule,
    RouterModule,
    RouterModule.forChild(routes),
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
    MatLineModule,
    TranslateModule,
    MatButtonModule,
    FlexLayoutModule,
    MatInputModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatSelectModule, QuillModule, MatRippleModule, MatChipsModule, MatSlideToggleModule
  ]
  , exports: [RouterModule],
  bootstrap: [UserComponent],
})
export class UserModule {

}
