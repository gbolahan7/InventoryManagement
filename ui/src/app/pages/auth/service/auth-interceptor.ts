
import { Inject, Injectable, Injector } from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, switchMap} from 'rxjs/operators';
import {AuthToken} from "../model/token";
import {AuthService} from "./auth.service";
import {AUTH_TOKEN_INTERCEPTOR_FILTER} from "../auth.options";

@Injectable()
export class AuthJWTInterceptor implements HttpInterceptor {

  constructor(private injector: Injector,
              @Inject(AUTH_TOKEN_INTERCEPTOR_FILTER) protected filter, authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.filter(req)) {
      return this.authService.isAuthenticatedOrRefresh()
        .pipe(
          switchMap(authenticated => {
            if (authenticated) {
              return this.authService.getToken().pipe(
                switchMap((token: AuthToken) => {
                  const JWT = `Bearer ${token.getValue()}`;
                  req = req.clone({
                    setHeaders: {
                      Authorization: JWT,
                    },
                  });
                  return next.handle(req);
                }),
                catchError((error: HttpErrorResponse) => {
                  if (error && error.status == 401)
                    this.authService.logout().subscribe();
                    return throwError(error);
                }
              ))
            } else {
              return next.handle(req);
            }
          }),
        )
    } else {
      return next.handle(req);
    }
  }

  protected get authService(): AuthService {
    return this.injector.get(AuthService);
  }

}
