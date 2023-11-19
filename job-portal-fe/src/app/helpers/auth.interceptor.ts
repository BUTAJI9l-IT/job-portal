import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {Inject, Injectable, Optional} from '@angular/core';

import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {catchError, filter, switchMap, take} from 'rxjs/operators';
import {StorageService} from "../service/storage.service";
import {AuthorizationService} from "../../api/authorization.service";
import {LoginResponse} from "../../model/loginResponse";
import {BASE_PATH} from "../../variables";
import {Router} from "@angular/router";
import {EventBusService} from "../service/event-bus.service";
import {EventData} from "../../model/event.class";
import {error} from "@angular/compiler-cli/src/transformers/util";

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  protected basePath = 'http://localhost:8080';

  constructor(private storageService: StorageService, private authService: AuthorizationService, @Optional() @Inject(BASE_PATH) basePath: string,
              private router: Router, private eventBusService: EventBusService) {
    if (basePath) {
      this.basePath = basePath;
    }
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<Object>> {
    if (req.url.startsWith(this.basePath + "/auth")) {
      return next.handle(req);
    }
    let authReq = req;
    const token = this.storageService.getTokens()?.accessToken;
    if (token != null) {
      authReq = this.addTokenHeader(req, token);
    }

    return next.handle(authReq).pipe(catchError(error => {
      if (error instanceof HttpErrorResponse && error.status === 401) {
        return this.handle401Error(authReq, next);
      }
      if (error instanceof HttpErrorResponse && error.status === 403) {
        this.handle403Error();
      }
      if (error instanceof HttpErrorResponse && error.status === 404) {
        this.handle404Error();
      }

      return throwError(error);
    }));
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);
      const token = this.storageService.getTokens();

      if (token) {
        return this.authService.refreshToken({refreshToken: token.refreshToken!}).pipe(
          switchMap((token: LoginResponse) => {
            this.isRefreshing = false;

            this.storageService.saveTokens(token);
            this.refreshTokenSubject.next(token.refreshToken);

            return next.handle(this.addTokenHeader(request, token.accessToken!));
          }),
          catchError((err) => {
            this.isRefreshing = false;
            this.storageService.clean();
            return throwError(err)
          })
        );
      }
    }
    this.eventBusService.emit(new EventData('logout', null));
    return this.refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token) => {
        return next.handle(this.addTokenHeader(request, token))
      })
    );
  }

  private handle403Error() {
    this.router.navigate(["/forbidden"]).then(() => {
    })
  }

  private handle404Error() {
    this.router.navigate(["/not-found"]).then(() => {
    })
  }

  private addTokenHeader(request: HttpRequest<any>, token: string) {
    return request.clone({headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token)});
  }
}

export const authInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
];
