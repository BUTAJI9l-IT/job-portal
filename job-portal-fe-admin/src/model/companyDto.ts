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

export interface CompanyDto { 
    companyName?: string;
    companySize?: CompanyDto.CompanySizeEnum;
    id?: string;
    userId?: string;
}
export namespace CompanyDto {
    export type CompanySizeEnum = 'MICRO' | 'SMALL' | 'MEDIUM' | 'LARGE' | 'CORPORATION';
    export const CompanySizeEnum = {
        MICRO: 'MICRO' as CompanySizeEnum,
        SMALL: 'SMALL' as CompanySizeEnum,
        MEDIUM: 'MEDIUM' as CompanySizeEnum,
        LARGE: 'LARGE' as CompanySizeEnum,
        CORPORATION: 'CORPORATION' as CompanySizeEnum
    };
}