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
 *//* tslint:disable:no-unused-variable member-ordering */

import {Inject, Injectable, Optional} from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpParams, HttpResponse} from '@angular/common/http';
import {CustomHttpUrlEncodingCodec} from '../encoder';

import {Observable} from 'rxjs';

import {ApplicationDetailDto} from '../model/applicationDetailDto';
import {ApplicationUpdateDto} from '../model/applicationUpdateDto';
import {PageApplicationDto} from '../model/pageApplicationDto';

import {BASE_PATH} from '../variables';
import {Configuration} from '../configuration';


@Injectable()
export class ApplicationService {

  protected basePath = 'http://localhost:8080';
    public defaultHeaders = new HttpHeaders();
    public configuration = new Configuration();

    constructor(protected httpClient: HttpClient, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
            this.basePath = basePath || configuration.basePath || this.basePath;
        }
    }

    /**
     * @param consumes string[] mime-types
     * @return true: consumes contains 'multipart/form-data', false: otherwise
     */
    private canConsumeForm(consumes: string[]): boolean {
        const form = 'multipart/form-data';
        for (const consume of consumes) {
            if (form === consume) {
                return true;
            }
        }
        return false;
    }


    /**
     * Change a state of an application
     * Company can change a state an application
     * @param body
     * @param applicationId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public changeApplicationState(body: ApplicationUpdateDto, applicationId: string, observe?: 'body', reportProgress?: boolean): Observable<ApplicationDetailDto>;
    public changeApplicationState(body: ApplicationUpdateDto, applicationId: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<ApplicationDetailDto>>;
    public changeApplicationState(body: ApplicationUpdateDto, applicationId: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<ApplicationDetailDto>>;
    public changeApplicationState(body: ApplicationUpdateDto, applicationId: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling changeApplicationState.');
        }

        if (applicationId === null || applicationId === undefined) {
            throw new Error('Required parameter applicationId was null or undefined when calling changeApplicationState.');
        }

        let headers = this.defaultHeaders;

        // authentication (auth_schema) required
        if (this.configuration.accessToken) {
            const accessToken = typeof this.configuration.accessToken === 'function'
                ? this.configuration.accessToken()
                : this.configuration.accessToken;
            headers = headers.set('Authorization', 'Bearer ' + accessToken);
        }
        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<ApplicationDetailDto>('put',`${this.basePath}/applications/${encodeURIComponent(String(applicationId))}/state`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Delete an application
     *
     * @param applicationId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deleteApplication(applicationId: string, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public deleteApplication(applicationId: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public deleteApplication(applicationId: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public deleteApplication(applicationId: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (applicationId === null || applicationId === undefined) {
            throw new Error('Required parameter applicationId was null or undefined when calling deleteApplication.');
        }

        let headers = this.defaultHeaders;

        // authentication (auth_schema) required
        if (this.configuration.accessToken) {
            const accessToken = typeof this.configuration.accessToken === 'function'
                ? this.configuration.accessToken()
                : this.configuration.accessToken;
            headers = headers.set('Authorization', 'Bearer ' + accessToken);
        }
        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<any>('delete',`${this.basePath}/applications/${encodeURIComponent(String(applicationId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Returns an application by given id
     *
     * @param applicationId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getApplication(applicationId: string, observe?: 'body', reportProgress?: boolean): Observable<ApplicationDetailDto>;
    public getApplication(applicationId: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<ApplicationDetailDto>>;
    public getApplication(applicationId: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<ApplicationDetailDto>>;
    public getApplication(applicationId: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (applicationId === null || applicationId === undefined) {
            throw new Error('Required parameter applicationId was null or undefined when calling getApplication.');
        }

        let headers = this.defaultHeaders;

        // authentication (auth_schema) required
        if (this.configuration.accessToken) {
            const accessToken = typeof this.configuration.accessToken === 'function'
                ? this.configuration.accessToken()
                : this.configuration.accessToken;
            headers = headers.set('Authorization', 'Bearer ' + accessToken);
        }
        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<ApplicationDetailDto>('get',`${this.basePath}/applications/${encodeURIComponent(String(applicationId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Returns all applications
     *
     * @param q
     * @param applicant
     * @param jobPosition
     * @param company
     * @param status
     * @param dateFrom
     * @param dateTo
     * @param page Zero-based page index (0..N)
     * @param size The size of the page to be returned
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getApplications(q?: Array<string>, applicant?: string, jobPosition?: string, company?: string, status?: string, dateFrom?: Date, dateTo?: Date, page?: number, size?: number, sort?: Array<string>, observe?: 'body', reportProgress?: boolean): Observable<PageApplicationDto>;
    public getApplications(q?: Array<string>, applicant?: string, jobPosition?: string, company?: string, status?: string, dateFrom?: Date, dateTo?: Date, page?: number, size?: number, sort?: Array<string>, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<PageApplicationDto>>;
    public getApplications(q?: Array<string>, applicant?: string, jobPosition?: string, company?: string, status?: string, dateFrom?: Date, dateTo?: Date, page?: number, size?: number, sort?: Array<string>, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<PageApplicationDto>>;
    public getApplications(q?: Array<string>, applicant?: string, jobPosition?: string, company?: string, status?: string, dateFrom?: Date, dateTo?: Date, page?: number, size?: number, sort?: Array<string>, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {











        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (q) {
            q.forEach((element) => {
                queryParameters = queryParameters.append('q', <any>element);
            })
        }
        if (applicant !== undefined && applicant !== null) {
            queryParameters = queryParameters.set('applicant', <any>applicant);
        }
        if (jobPosition !== undefined && jobPosition !== null) {
            queryParameters = queryParameters.set('jobPosition', <any>jobPosition);
        }
        if (company !== undefined && company !== null) {
            queryParameters = queryParameters.set('company', <any>company);
        }
        if (status !== undefined && status !== null) {
            queryParameters = queryParameters.set('status', <any>status);
        }
        if (dateFrom !== undefined && dateFrom !== null) {
            queryParameters = queryParameters.set('dateFrom', <any>dateFrom.toISOString());
        }
        if (dateTo !== undefined && dateTo !== null) {
            queryParameters = queryParameters.set('dateTo', <any>dateTo.toISOString());
        }
        if (page !== undefined && page !== null) {
            queryParameters = queryParameters.set('page', <any>page);
        }
        if (size !== undefined && size !== null) {
            queryParameters = queryParameters.set('size', <any>size);
        }
        if (sort) {
            sort.forEach((element) => {
                queryParameters = queryParameters.append('sort', <any>element);
            })
        }

        let headers = this.defaultHeaders;

        // authentication (auth_schema) required
        if (this.configuration.accessToken) {
            const accessToken = typeof this.configuration.accessToken === 'function'
                ? this.configuration.accessToken()
                : this.configuration.accessToken;
            headers = headers.set('Authorization', 'Bearer ' + accessToken);
        }
        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<PageApplicationDto>('get',`${this.basePath}/applications`,
            {
                params: queryParameters,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
