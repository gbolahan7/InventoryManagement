import {Inject, Injectable} from '@angular/core';

import {Observable, of as observableOf} from 'rxjs';
import {catchError, filter, map, switchMap} from 'rxjs/operators';

import {TokenService} from '../token/token.service';
import {AuthCredential, AuthToken, AuthTokenError} from "../model/token";
import {AuthResponse} from "../model/auth-response";
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {AuthTokenWrapper} from "../token/token-wrapper";
import {BASE_PATH} from "../../../app.module";
import {AuthUser} from "../model/auth-user";

@Injectable()
export class AuthService {
  private basePath: string;

  constructor(protected tokenService: TokenService, protected http: HttpClient,
              protected tokenWrapper: AuthTokenWrapper, @Inject(BASE_PATH) basePath: string,) {
    this.basePath = basePath;
  }

  getUser(): Observable<AuthUser> {
    return this.getToken().pipe(
      filter((token: AuthToken) => token.isValid()),
      map((token: AuthToken) => {
        let user: AuthUser =  new AuthUser();
        user.name = token.getPayload().sub;
        user.permissions = token.getPayload().permissions;
        user.roles = token.getPayload().roles;
        return user;
      })
    );
  }

  getToken(): Observable<AuthToken> {
    return this.tokenService.get();
  }

  isAuthenticated(): Observable<boolean> {
    return this.getToken()
      .pipe(map((token: AuthToken) => token.isValid()));
  }

  isAuthenticatedOrRefresh(): Observable<boolean> {
    return this.getToken()
      .pipe(
        switchMap(token => {
          if (token.getValue() && !token.isValid()) {
            return this.refreshToken(token)
              .pipe(
                switchMap(res => {
                  if (res.isSuccess()) {
                    return this.isAuthenticated();
                  } else {
                    return observableOf(false);
                  }
                }),
              )
          } else {
            return observableOf(token.isValid());
          }
        }));
  }

  onTokenChange(): Observable<AuthToken> {
    return this.tokenService.tokenChange();
  }

  onAuthenticationChange(): Observable<boolean> {
    return this.onTokenChange()
      .pipe(map((token: AuthToken) => token.isValid()));
  }

  authenticate(data?: any): Observable<AuthResponse> {
    return this.authenticateHelper(data)
      .pipe(
        switchMap((result: AuthResponse) => {
          return this.processResultToken(result);
        }),
      );
  }

  logout(): Observable<AuthResponse> {
    return this.logoutHelper_()
      .pipe(
        switchMap((result: AuthResponse) => {
          if (result.isSuccess()) {
            this.tokenService.clear()
              .pipe(map(() => result));
          }
          return observableOf(result);
        }),
      );
  }

  register(data?: any): Observable<AuthResponse> {
    return this.registerHelper_(data)
      .pipe(
        switchMap((result: AuthResponse) => {
          return this.processResultToken(result);
        }),
      );
  }

  refreshToken(data?: any): Observable<AuthResponse> {
    return this.refreshTokenHelper_(data)
      .pipe(
        switchMap((result: AuthResponse) => {
          return this.processResultToken(result);
        }),
      );
  }

  private processResultToken(result: AuthResponse) {
    if (result.isSuccess() && result.getToken()) {
      return this.tokenService.set(result.getToken())
        .pipe(
          map((token: AuthResponse) => {
            return result;
          }),
        );
    }
    return observableOf(result);
  }

  private authenticateHelper(data?: any): Observable<AuthResponse> {
    const module = 'login';
    return this.http.post(`${this.basePath}/api/auth/login`, data, {observe: 'response', headers: {}}).pipe(
      map((res: HttpResponse<AuthCredential>) => {
        return new AuthResponse(
          true,
          res,
          '/',
          [],
          [],
          this.tokenWrapper.unwrap(res.body.data.token),
        );
      }),
      catchError((res) => {
        return this.handleResponseError(res, module);
      }),
    );
  }

  private logoutHelper_(): Observable<AuthResponse> {
    return observableOf(new AuthResponse(true, null, 'auth/login'))
  }

  private registerHelper_(data?: any): Observable<AuthResponse> {
    const module = 'register';
    return this.http.post(`${this.basePath}/api/auth/signup`, data, {observe: 'response', headers: {}}).pipe(
      map((res) => {
        return new AuthResponse(
          true,
          res,
          'auth/login',
          [],
          ['Successfully registered'],
          null,
        );
      }),
      catchError((res) => {
        return this.handleResponseError(res, module);
      }),
    )
  }

  private refreshTokenHelper_(data?: any): Observable<AuthResponse> {
    const module = 'refreshToken';

    return this.http.get(`${this.basePath}/api/auth/refresh`, {observe: 'response', headers: {}}).pipe(
      map((res) => {
        return new AuthResponse(
          true,
          res,
          '',
          [],
          [],
          null,
        );
      }),
      catchError((res) => {
        return this.handleResponseError(res, module);
      }),
    );
  }

  private handleResponseError(res: any, module: string): Observable<AuthResponse> {
    let errors = ['Occurred at ' + module];
    if (res instanceof HttpErrorResponse) {
      let body: any = res.error;
      if (res.status === 422) {
        errors.push(...body.data.message);
      } else if (res.status === 401) {
        errors.push(body.data.message);
      } else errors.push('Server error');
    } else if (res instanceof AuthTokenError) {
      errors.push(res.message);
    } else {
      errors.push('Something went wrong.');
    }
    return observableOf(new AuthResponse(false, res, null, errors, null, null));
  }
}
