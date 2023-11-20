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
import {PageUserDto} from '../model/pageUserDto';
import {PreferencesDto} from '../model/preferencesDto';
import {UserDto} from '../model/userDto';
import {UserUpdateDto} from '../model/userUpdateDto';

import {BASE_PATH} from '../variables';
import {Configuration} from '../configuration';


@Injectable()
export class UserService {

  protected basePath = 'http://localhost:8080';
  public defaultHeaders = new HttpHeaders();
  public configuration = new Configuration();

  constructor(protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
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
   * Delete old user avatar
   *
   * @param userId
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public deleteAvatar(userId: string, observe?: 'body', reportProgress?: boolean): Observable<Blob>;
  public deleteAvatar(userId: string, observe: any = 'body', reportProgress: boolean = false): Observable<Blob> {

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling deleteAvatar.');
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
    const consumes: string[] = [];

    return this.httpClient.request('delete', `${this.basePath}/users/${encodeURIComponent(String(userId))}/avatar`,
      {
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress,
        responseType: "blob"
      }
    );
  }

  /**
   * Delete a user
   *
   * @param userId
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public deleteUser(userId: string, observe?: 'body', reportProgress?: boolean): Observable<any>;
  public deleteUser(userId: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
  public deleteUser(userId: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
  public deleteUser(userId: string, observe: any = 'body', reportProgress: boolean = false): Observable<any> {

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling deleteUser.');
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
    let httpHeaderAccepts: string[] = [];
    const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
    if (httpHeaderAcceptSelected != undefined) {
      headers = headers.set('Accept', httpHeaderAcceptSelected);
    }

    // to determine the Content-Type header
    const consumes: string[] = [];

    return this.httpClient.request<any>('delete', `${this.basePath}/users/${encodeURIComponent(String(userId))}`,
      {
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress
      }
    );
  }

  /**
   * Get user avatar
   *
   * @param userId
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public getAvatar(userId: string, observe?: 'body', reportProgress?: boolean): Observable<Blob>;
  public getAvatar(userId: string, observe: any = 'body', reportProgress: boolean = false): Observable<Blob> {

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling getAvatar.');
    }

    let headers = this.defaultHeaders;

    // to determine the Accept header
    let httpHeaderAccepts: string[] = [
      'application/json'
    ];
    const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
    if (httpHeaderAcceptSelected != undefined) {
      headers = headers.set('Accept', httpHeaderAcceptSelected);
    }

    // to determine the Content-Type header
    const consumes: string[] = [];

    return this.httpClient.request('get', `${this.basePath}/users/${encodeURIComponent(String(userId))}/avatar`,
      {
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress,
        responseType: "blob"
      }
    );
  }

  /**
   * Get user avatar
   *
   * @param userId
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public getAvatarSecure(userId: string, observe?: 'body', reportProgress?: boolean): Observable<Blob>;
  public getAvatarSecure(userId: string, observe: any = 'body', reportProgress: boolean = false): Observable<Blob> {

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling getAvatar.');
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
    const consumes: string[] = [];

    return this.httpClient.request('get', `${this.basePath}/users/${encodeURIComponent(String(userId))}/avatar-secure`,
      {
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress,
        responseType: "blob"
      }
    );
  }

  /**
   * Get user info
   *
   * @param userId
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public getUserInfo(userId: string, observe?: 'body', reportProgress?: boolean): Observable<UserDto>;
  public getUserInfo(userId: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<UserDto>>;
  public getUserInfo(userId: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<UserDto>>;
  public getUserInfo(userId: string, observe: any = 'body', reportProgress: boolean = false): Observable<any> {

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling getUserInfo.');
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
    const consumes: string[] = [];

    return this.httpClient.request<UserDto>('get', `${this.basePath}/users/${encodeURIComponent(String(userId))}`,
      {
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress
      }
    );
  }

  /**
   * Get user preferences
   *
   * @param userId
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public getUserPreferences(userId: string, observe?: 'body', reportProgress?: boolean): Observable<PreferencesDto>;
  public getUserPreferences(userId: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<PreferencesDto>>;
  public getUserPreferences(userId: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<PreferencesDto>>;
  public getUserPreferences(userId: string, observe: any = 'body', reportProgress: boolean = false): Observable<any> {

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling getUserPreferences.');
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
    const consumes: string[] = [];

    return this.httpClient.request<PreferencesDto>('get', `${this.basePath}/users/${encodeURIComponent(String(userId))}/preferences`,
      {
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress
      }
    );
  }

  /**
   * Returns all users
   *
   * @param q
   * @param scope
   * @param page Zero-based page index (0..N)
   * @param size The size of the page to be returned
   * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public getUsers(q?: Array<string>, scope?: string, page?: number, size?: number, sort?: Array<string>, observe?: 'body', reportProgress?: boolean): Observable<PageUserDto>;
  public getUsers(q?: Array<string>, scope?: string, page?: number, size?: number, sort?: Array<string>, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<PageUserDto>>;
  public getUsers(q?: Array<string>, scope?: string, page?: number, size?: number, sort?: Array<string>, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<PageUserDto>>;
  public getUsers(q?: Array<string>, scope?: string, page?: number, size?: number, sort?: Array<string>, observe: any = 'body', reportProgress: boolean = false): Observable<any> {


    let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
    if (q) {
      q.forEach((element) => {
        queryParameters = queryParameters.append('q', <any>element);
      })
    }
    if (scope !== undefined && scope !== null) {
      queryParameters = queryParameters.set('scope', <any>scope);
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
    const consumes: string[] = [];

    return this.httpClient.request<PageUserDto>('get', `${this.basePath}/users`,
      {
        params: queryParameters,
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress
      }
    );
  }

  /**
   * Updates user&#x27;s password
   *
   * @param body
   * @param userId
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public updatePassword(body: UserUpdateDto, userId: string, observe?: 'body', reportProgress?: boolean): Observable<UserDto>;
  public updatePassword(body: UserUpdateDto, userId: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<UserDto>>;
  public updatePassword(body: UserUpdateDto, userId: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<UserDto>>;
  public updatePassword(body: UserUpdateDto, userId: string, observe: any = 'body', reportProgress: boolean = false): Observable<any> {

    if (body === null || body === undefined) {
      throw new Error('Required parameter body was null or undefined when calling updatePassword.');
    }

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling updatePassword.');
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

    return this.httpClient.request<UserDto>('put', `${this.basePath}/users/${encodeURIComponent(String(userId))}`,
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
   * Update user preferences
   *
   * @param body
   * @param userId
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public updatePreferences(body: PreferencesDto, userId: string, observe?: 'body', reportProgress?: boolean): Observable<PreferencesDto>;
  public updatePreferences(body: PreferencesDto, userId: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<PreferencesDto>>;
  public updatePreferences(body: PreferencesDto, userId: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<PreferencesDto>>;
  public updatePreferences(body: PreferencesDto, userId: string, observe: any = 'body', reportProgress: boolean = false): Observable<any> {

    if (body === null || body === undefined) {
      throw new Error('Required parameter body was null or undefined when calling updatePreferences.');
    }

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling updatePreferences.');
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

    return this.httpClient.request<PreferencesDto>('put', `${this.basePath}/users/${encodeURIComponent(String(userId))}/preferences`,
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
   * Upload new user avatar
   *
   * @param userId
   * @param file
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public uploadAvatarForm(userId: string, file?: Blob, observe?: 'body', reportProgress?: boolean): Observable<Blob>;
  public uploadAvatarForm(userId: string, file?: Blob, observe: any = 'body', reportProgress: boolean = false): Observable<Blob> {

    if (userId === null || userId === undefined) {
      throw new Error('Required parameter userId was null or undefined when calling uploadAvatar.');
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
      'multipart/form-data'
    ];

    const canConsumeForm = this.canConsumeForm(consumes);

    let formParams: { append(param: string, value: any): void; };
    let useForm = false;
    let convertFormParamsToString = false;
    // use FormData to transmit files using content-type "multipart/form-data"
    // see https://stackoverflow.com/questions/4007969/application-x-www-form-urlencoded-or-multipart-form-data
    useForm = canConsumeForm;
    if (useForm) {
      formParams = new FormData();
    } else {
      formParams = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
    }

    if (file !== undefined) {
      formParams = formParams.append('file', <any>file) as any || formParams;
    }

    return this.httpClient.request('post', `${this.basePath}/users/${encodeURIComponent(String(userId))}/avatar`,
      {
        body: convertFormParamsToString ? formParams.toString() : formParams,
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress,
        responseType: "blob"
      }
    );
  }

}
