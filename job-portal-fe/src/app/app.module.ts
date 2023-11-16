import {APP_INITIALIZER, Injector, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgSelectModule} from '@ng-select/ng-select';
import {HomeComponent} from './home/home/containers/home.component';
import {RouterModule, Routes} from "@angular/router";
import {AppComponent} from "./app.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {HttpClient, HttpClientModule} from "@angular/common/http";
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
import {TranslateLoader, TranslateModule, TranslateService} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {MatMenuModule} from "@angular/material/menu";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {NotFoundComponent} from './home/components/not-found/not-found.component';
import {ForbiddenComponent} from "./home/components/forbidden/forbidden.component";
import {UserComponent} from "./user/components/user/user.component";
import {SharedModule} from "./component/shared.module";
import {QuillModule} from "ngx-quill";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {loadingInterceptorProviders} from "./helpers/loading.interceptor";
import {ApplicationInitializerFactory} from "./helpers/translation.config";


const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'home', component: HomeComponent},
  {path: 'companies', loadChildren: () => import('./companies/companies.module').then(x => x.CompaniesModule)},
  {path: 'jobs', loadChildren: () => import('./jobs/jobs.module').then(x => x.JobsModule)},
  {path: 'users', loadChildren: () => import('./users/users.module').then(x => x.UsersModule)},
  {path: 'user', component: UserComponent, loadChildren: () => import('./user/user.module').then(x => x.UserModule)},

  {path: 'forbidden', pathMatch: 'full', component: ForbiddenComponent},
  {path: '**', pathMatch: 'full', component: NotFoundComponent}
]

@NgModule({
  declarations: [AppComponent, HomeComponent],
  imports: [
    SharedModule,
    HttpClientModule,
    BrowserModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    NgSelectModule,
    FormsModule,
    RouterModule.forRoot(routes),
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
    QuillModule.forRoot(),
    MatProgressBarModule
  ],
  providers: [
    {provide: LOCALE_ID, useValue: 'en'},
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher},
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2500, horizontalPosition: "end"}},
    authInterceptorProviders, loadingInterceptorProviders,
    {
      provide: APP_INITIALIZER,
      useFactory: ApplicationInitializerFactory,
      deps: [TranslateService, Injector],
      multi: true
    },
  ],
  bootstrap: [AppComponent],
  exports: [RouterModule, TranslateModule]
})
export class AppModule {
}

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
