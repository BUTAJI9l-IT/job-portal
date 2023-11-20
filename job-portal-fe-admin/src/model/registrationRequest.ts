/**
 * job-portal
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: local
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import {RepeatPasswordDto} from './repeatPasswordDto';

export interface RegistrationRequest { 
    companyLink?: string;
    companyName?: string;
    companySize?: RegistrationRequest.CompanySizeEnum;
    email: string;
    language?: string;
    lastName?: string;
    name?: string;
    password?: RepeatPasswordDto;
    scope: RegistrationRequest.ScopeEnum;
}
export namespace RegistrationRequest {
    export type CompanySizeEnum = 'MICRO' | 'SMALL' | 'MEDIUM' | 'LARGE' | 'CORPORATION';
    export const CompanySizeEnum = {
        MICRO: 'MICRO' as CompanySizeEnum,
        SMALL: 'SMALL' as CompanySizeEnum,
        MEDIUM: 'MEDIUM' as CompanySizeEnum,
        LARGE: 'LARGE' as CompanySizeEnum,
        CORPORATION: 'CORPORATION' as CompanySizeEnum
    };
    export type ScopeEnum = 'ADMIN' | 'COMPANY' | 'REGULAR_USER';
    export const ScopeEnum = {
        ADMIN: 'ADMIN' as ScopeEnum,
        COMPANY: 'COMPANY' as ScopeEnum,
        REGULARUSER: 'REGULAR_USER' as ScopeEnum
    };
}