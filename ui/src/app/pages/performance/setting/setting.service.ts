import {Inject, Injectable, Optional} from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpHeaders,
  HttpParameterCodec,
  HttpParams,
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {BASE_PATH} from "../../../app.module";
import {
  CustomHttpParameterCodec,
  GenericResponse,
  PageRequest,
  PageResponse
} from "../../../core/utils/template/http-util";
import {
  PerformanceSetting,
  PerformanceSettingAudit,
  PerformanceSettingHistoryAudit,
  PerformanceSettingRequest,
  StaffPerformance
} from "./setting.data";
import {ErrorService} from "../../../core/utils/error.service";
import {catchError} from "rxjs/operators";



@Injectable()
export class SettingService {

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


  public getPerformanceSetting(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PerformanceSetting>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPerformanceSetting.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PerformanceSetting>>(`${this.basePath}/performance/setting/${encodeURIComponent(String(id))}`,
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

  public getPerformanceSettingAudit(id: number, revisionId: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PerformanceSettingHistoryAudit>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPerformanceSetting.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PerformanceSettingHistoryAudit>>(`${this.basePath}/performance/setting/audit/id/${encodeURIComponent(String(id))}/revision/${encodeURIComponent(String(revisionId))}`,
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

  public getPerformanceSettingRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PerformanceSettingRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPerformanceSetting.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PerformanceSettingRequest>>(`${this.basePath}/performance/setting/request/${encodeURIComponent(String(id))}`,
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


  public getPerformanceSettingAudits(id: number, pageRequest?: PageRequest, performanceSettingAuditsFilter?: { [key: string]: object; }, observe: any = 'body', reportProgress: boolean = false, options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<PerformanceSettingHistoryAudit>>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPerformanceSettingAudits.');
    }

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (performanceSettingAuditsFilter !== undefined && performanceSettingAuditsFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>performanceSettingAuditsFilter, 'performanceSettingAuditsFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<PerformanceSettingHistoryAudit>>>(`${this.basePath}/performance/setting/audit/id/${encodeURIComponent(String(id))}`,
      {
        params: queryParameters,
        responseType: 'json',
        headers: headers,
        observe: observe,
        reportProgress: reportProgress
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }

  public getPerformanceStaffs(  observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<StaffPerformance[]>>> {
    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<StaffPerformance[]>>(`${this.basePath}/performance/staff`,
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

  public getPerformanceSettingRequests( pageRequest?: PageRequest, performanceSettingRequestFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<PerformanceSettingRequest>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (performanceSettingRequestFilter !== undefined && performanceSettingRequestFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>performanceSettingRequestFilter, 'performanceSettingRequestFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<PerformanceSettingRequest>>>(`${this.basePath}/performance/setting/request`,
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

  public createPerformanceSettingRequest(performanceSettingBody: PerformanceSetting, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (performanceSettingBody === null || performanceSettingBody === undefined) {
      throw new Error('Required parameter performanceSettingBody was null or undefined when calling createPerformanceSettingRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/performance/setting/request/create`,
      performanceSettingBody,
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

  public editPerformanceSettingRequest(performanceSettingBody: PerformanceSetting, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (performanceSettingBody === null || performanceSettingBody === undefined) {
      throw new Error('Required parameter performanceSettingBody was null or undefined when calling createPerformanceSettingRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/performance/setting/request/modify`,
      performanceSettingBody,
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

  public approvePerformanceSettingRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PerformanceSettingRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPerformanceSetting.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PerformanceSettingRequest>>(`${this.basePath}/performance/setting/request/approve/${encodeURIComponent(String(id))}`,
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

  public rejectPerformanceSettingRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PerformanceSettingRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPerformanceSetting.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PerformanceSettingRequest>>(`${this.basePath}/performance/setting/request/reject/${encodeURIComponent(String(id))}`,
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
