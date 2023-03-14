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
import { PageableObject } from './pageableObject';
import { Sort } from './sort';
import { UserDto } from './userDto';

export interface PageUserDto { 
    content?: Array<UserDto>;
    empty?: boolean;
    first?: boolean;
    last?: boolean;
    number?: number;
    numberOfElements?: number;
    pageable?: PageableObject;
    size?: number;
    sort?: Sort;
    totalElements?: number;
    totalPages?: number;
}