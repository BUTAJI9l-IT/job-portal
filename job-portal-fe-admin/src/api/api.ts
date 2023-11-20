export * from './applicant.service';
import {ApplicantService} from './applicant.service';
import {ApplicationService} from './application.service';
import {AuthorizationService} from './authorization.service';
import {CompanyService} from './company.service';
import {JobPositionService} from './jobPosition.service';
import {JobPositionCategoryService} from './jobPositionCategory.service';
import {UserService} from './user.service';

export * from './application.service';
export * from './authorization.service';
export * from './company.service';
export * from './jobPosition.service';
export * from './jobPositionCategory.service';
export * from './user.service';
export const APIS = [ApplicantService, ApplicationService, AuthorizationService, CompanyService, JobPositionService, JobPositionCategoryService, UserService];
