import {HTTP_INTERCEPTORS, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {LoadingService} from "../service/loading.service";
import {finalize} from "rxjs";

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  private totalRequests = 0;

  constructor(private loadingService: LoadingService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    this.totalRequests++;
    this.loadingService.loadingOn();

    return next.handle(request).pipe(
      finalize(() => {
        this.totalRequests--;
        if (this.totalRequests === 0) {
          this.loadingService.loadingOff();
        }
      })
    );
  }
}

export const loadingInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true}
];
