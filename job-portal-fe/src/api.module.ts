import {NgModule, Optional, SkipSelf} from '@angular/core';
import {HttpClient} from '@angular/common/http';


import {ApplicantService} from './api/applicant.service';
import {ApplicationService} from './api/application.service';
import {AuthorizationService} from './api/authorization.service';
import {CompanyService} from './api/company.service';
import {JobPositionService} from './api/jobPosition.service';
import {JobPositionCategoryService} from './api/jobPositionCategory.service';
import {UserService} from './api/user.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    ApplicantService,
    ApplicationService,
    AuthorizationService,
    CompanyService,
    JobPositionService,
    JobPositionCategoryService,
    UserService ]
})
export class ApiModule {
  constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
