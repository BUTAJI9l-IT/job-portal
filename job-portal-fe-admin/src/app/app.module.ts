import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgSelectModule} from '@ng-select/ng-select';
import {RouterModule, Routes} from "@angular/router";
import {AppComponent} from "./app.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {HttpClientModule} from "@angular/common/http";
import {MatChipsModule} from "@angular/material/chips";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {ErrorStateMatcher, MatRippleModule, ShowOnDirtyErrorStateMatcher} from "@angular/material/core";
import {MatTabsModule} from "@angular/material/tabs";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatTableModule} from "@angular/material/table";
import {MAT_SNACK_BAR_DEFAULT_OPTIONS, MatSnackBarModule} from "@angular/material/snack-bar";
import {ApiModule} from "../api/api.module";
import {authInterceptorProviders} from "./helpers/auth.interceptor";
import {MatMenuModule} from "@angular/material/menu";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {NotFoundComponent} from './home/components/not-found/not-found.component';
import {ForbiddenComponent} from "./home/components/forbidden/forbidden.component";
import {SharedModule} from "./component/shared.module";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {loadingInterceptorProviders} from "./helpers/loading.interceptor";
import {LoginComponent} from './login/login.component';
import {UsersComponent} from './users/users.component';
import {ApplicantsComponent} from './applicants/applicants.component';
import {CompaniesComponent} from './companies/companies.component';
import {JobPositionsComponent} from './job-positions/job-positions.component';
import {ApplicationsComponent} from './applications/applications.component';
import {CategoriesComponent} from './categories/categories.component';
import {UserComponent} from './users/detail/user.component';
import {JobPositionComponent} from "./job-positions/detail/job-position.component";
import {UserAccountAdminComponent} from "./users/detail/user-account-admin/user-account-admin.component";
import {UserAccountApplicantComponent} from "./users/detail/user-account-applicant/user-account-applicant.component";
import {UserAccountCompanyComponent} from "./users/detail/user-account-company/user-account-company.component";
import {MatCardModule} from "@angular/material/card";
import {AvatarMenuComponent} from "./users/detail/avatar-menu/avatar-menu.component";
import {AbstractAccountComponent} from "./users/detail/abstract-account/abstract-account.component";
import {NgOptimizedImage} from "@angular/common";
import {QuillModule} from "ngx-quill";
import {ExperiencesListComponent} from './users/detail/experiences-list/experiences-list.component';


const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'login'},
    {path: 'login', component: LoginComponent},

    {path: 'users', component: UsersComponent},
    {path: 'applicants', component: ApplicantsComponent},
    {path: 'companies', component: CompaniesComponent},
    {path: 'job-positions', component: JobPositionsComponent},
    {path: 'applications', component: ApplicationsComponent},
    {path: 'categories', component: CategoriesComponent},
    {path: 'users/:id', component: UserComponent},
    {path: 'job-positions/:id', component: JobPositionComponent},

    {path: 'forbidden', pathMatch: 'full', component: ForbiddenComponent},
    {path: '**', pathMatch: 'full', component: NotFoundComponent}
]

@NgModule({
    declarations: [AppComponent, LoginComponent, UsersComponent, ApplicantsComponent, CompaniesComponent, JobPositionsComponent, ApplicationsComponent, CategoriesComponent, UserComponent, JobPositionComponent, UserAccountAdminComponent, UserAccountApplicantComponent, UserAccountCompanyComponent, AvatarMenuComponent, AbstractAccountComponent, ExperiencesListComponent],
    imports: [
        SharedModule,
        HttpClientModule,
        BrowserModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatSelectModule,
        MatButtonModule,
        NgSelectModule,
        FormsModule,
        RouterModule.forRoot(routes),
        QuillModule.forRoot(),
        MatToolbarModule,
        MatIconModule,
        MatChipsModule,
        MatDialogModule,
        MatInputModule,
        MatTabsModule,
        MatGridListModule,
        MatCheckboxModule,
        MatSnackBarModule,
        MatTableModule,
        ApiModule,
        MatMenuModule,
        MatSidenavModule,
        MatListModule,
        MatRippleModule,
        MatProgressBarModule,
        MatCardModule,
        NgOptimizedImage
    ],
    providers: [
        {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher},
        {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2500, horizontalPosition: "end"}},
        authInterceptorProviders, loadingInterceptorProviders,
    ],
    bootstrap: [AppComponent],
    exports: [RouterModule]
})
export class AppModule {
}

