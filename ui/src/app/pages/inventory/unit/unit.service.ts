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
import {Unit, UnitAudit, UnitHistoryAudit, UnitRequest} from "./unit.data";
import {ErrorService} from "../../../core/utils/error.service";
import {catchError} from "rxjs/operators";



@Injectable()
export class UnitService {

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


  public getUnit(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<Unit>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getUnit.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<Unit>>(`${this.basePath}/unit/${encodeURIComponent(String(id))}`,
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

  public deleteUnit(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<Unit>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getUnit.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<Unit>>(`${this.basePath}/unit/delete/${encodeURIComponent(String(id))}`,
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

  public getUnitAudit(id: number, revisionId: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<UnitHistoryAudit>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getUnit.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<UnitHistoryAudit>>(`${this.basePath}/unit/audit/id/${encodeURIComponent(String(id))}/revision/${encodeURIComponent(String(revisionId))}`,
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

  public getUnitRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<UnitRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getUnit.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<UnitRequest>>(`${this.basePath}/unit/request/${encodeURIComponent(String(id))}`,
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


  public getUnitAudits(id: number, pageRequest?: PageRequest, unitAuditsFilter?: { [key: string]: object; }, observe: any = 'body', reportProgress: boolean = false, options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<UnitHistoryAudit>>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getUnitAudits.');
    }

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (unitAuditsFilter !== undefined && unitAuditsFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>unitAuditsFilter, 'unitAuditsFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<UnitHistoryAudit>>>(`${this.basePath}/unit/audit/id/${encodeURIComponent(String(id))}`,
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

  public getUnits( pageRequest?: PageRequest, unitFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<Unit>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (unitFilter !== undefined && unitFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>unitFilter, 'unitFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<Unit>>>(`${this.basePath}/unit`,
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

  public getUnitRequests( pageRequest?: PageRequest, unitRequestFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<UnitRequest>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (unitRequestFilter !== undefined && unitRequestFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>unitRequestFilter, 'unitRequestFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<UnitRequest>>>(`${this.basePath}/unit/request`,
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

  public createUnitRequest(unitBody: Unit, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (unitBody === null || unitBody === undefined) {
      throw new Error('Required parameter unitBody was null or undefined when calling createUnitRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/unit/request/create`,
      unitBody,
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

  public editUnitRequest(unitBody: Unit, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (unitBody === null || unitBody === undefined) {
      throw new Error('Required parameter unitBody was null or undefined when calling createUnitRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/unit/request/modify`,
      unitBody,
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

  public approveUnitRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<UnitRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getUnit.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<UnitRequest>>(`${this.basePath}/unit/request/approve/${encodeURIComponent(String(id))}`,
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

  public rejectUnitRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<UnitRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getUnit.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<UnitRequest>>(`${this.basePath}/unit/request/reject/${encodeURIComponent(String(id))}`,
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
