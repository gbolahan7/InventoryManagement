import {Inject, Injectable, Optional} from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpHeaders,
  HttpParameterCodec,
  HttpParams,
  HttpResponse
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {BASE_PATH} from "../../../app.module";
import {
  CustomHttpParameterCodec,
  GenericResponse,
  PageRequest,
  PageResponse
} from "../../../core/utils/template/http-util";
import {ErrorService} from "../../../core/utils/error.service";
import {catchError} from "rxjs/operators";
import {Role} from "./role.data";
import {Category} from "../../inventory/category/category.data";



@Injectable()
export class RoleService {

  protected basePath = null;
  public defaultHeaders = new HttpHeaders();
  public encoder: HttpParameterCodec = new CustomHttpParameterCodec();

  constructor(private errorService: ErrorService, protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string) {
    this.basePath = basePath+'/api/v1';
    this.defaultHeaders.set('Accept', 'application/json' );
  }


  private addToHttpParams(httpParams: HttpParams, value: any, key?: string): HttpParams {
    if (typeof value === "object" && value instanceof Date === false) {
      httpParams = this.addToHttpParamsRecursive(httpParams, value);
    } else {
      httpParams = this.addToHttpParamsRecursive(httpParams, value, key);
    }
    return httpParams;
  }

  private addToHttpParamsRecursive(httpParams: HttpParams, value?: any, key?: string): HttpParams {
    if (value == null) {
      return httpParams;
    }

    if (typeof value === "object") {
      if (Array.isArray(value)) {
        (value as any[]).forEach(elem => httpParams = this.addToHttpParamsRecursive(httpParams, elem, key));
      } else if (value instanceof Date) {
        if (key != null) {
          httpParams = httpParams.append(key,
            (value as Date).toISOString().substr(0, 10));
        } else {
          throw Error("key may not be null if value is Date");
        }
      } else {
        Object.keys(value).forEach(k => httpParams = this.addToHttpParamsRecursive(
          httpParams, value[k], key != null ? `${key}.${k}` : k));
      }
    } else if (key != null) {
      httpParams = httpParams.append(key, value);
    } else {
      throw Error("key may not be null if value is not object or array");
    }
    return httpParams;
  }


  public getRole(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<Role>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getCategory.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<Role>>(`${this.basePath}/admin/role/${encodeURIComponent(String(id))}`,
      {
        responseType: 'json',
        headers: headers,
        observe: observe,
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }

  public getRoles( pageRequest?: PageRequest, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<Role>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<Role>>>(`${this.basePath}/admin/role`,
      {
        params: queryParameters,
        responseType: 'json',
        headers: headers,
        observe: observe,
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }

  public getPermissions(): Observable<GenericResponse<string[]>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<string[]>>(`${this.basePath}/admin/role/permission`,
      {
        params: queryParameters,
        responseType: 'json',
        headers: headers,
        observe: 'body',
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }

  public createRole(roleBody: Role, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (roleBody === null || roleBody === undefined) {
      throw new Error('Required parameter categoryBody was null or undefined when calling createCategoryRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/admin/role/create`,
      roleBody,
      {
        responseType: 'json',
        headers: headers,
        observe: observe,
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }

  public editRole(roleBody: Role, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (roleBody === null || roleBody === undefined) {
      throw new Error('Required parameter roleBody was null or undefined when calling editRole.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/admin/role/modify`,
      roleBody,
      {
        responseType: 'json',
        headers: headers,
        observe: observe,
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }


}
