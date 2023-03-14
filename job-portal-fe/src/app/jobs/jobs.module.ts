import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {JobsComponent} from "./components/jobs/jobs.component";
import {JobDetailComponent} from './job-detail/job-detail.component';
import {SharedModule} from "../component/shared.module";
import {MatCardModule} from "@angular/material/card";
import {MatDividerModule} from "@angular/material/divider";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {FlexLayoutModule} from "@angular/flex-layout";
import {TranslateModule} from "@ngx-translate/core";
import {JobApplicationsComponent} from './components/job-applications/job-applications.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {ReactiveFormsModule} from "@angular/forms";
import {JobApplicantCardComponent} from "./components/job-applicant-card/job-applicant-card.component";

const routes: Routes = [
  {path: '', component: JobsComponent},
  {path: ':id', component: JobDetailComponent},
  {
    path: ':id/applications',
    component: JobApplicationsComponent,
    children: [{
      path: ':applicantId',
      component: JobApplicantCardComponent
    }]
  },
];

@NgModule({
  declarations: [
    JobDetailComponent, JobsComponent, JobApplicationsComponent, JobApplicantCardComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatCardModule,
    MatDividerModule,
    MatIconModule,
    MatButtonModule,
    FlexLayoutModule,
    SharedModule,
    TranslateModule,
    MatSidenavModule,
    MatListModule,
    MatButtonToggleModule,
    ReactiveFormsModule,
  ]
})
export class JobsModule {
}
