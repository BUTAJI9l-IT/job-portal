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
import { ExperienceDto } from './experienceDto';

export interface ApplicantDetailDto { 
    city?: string;
    country?: string;
    email?: string;
    experiences?: Array<ExperienceDto>;
    id?: string;
    lastName?: string;
    name?: string;
    phone?: string;
    profile?: string;
    state?: string;
    userId?: string;
}