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
import {Category, CategoryAudit, CategoryHistoryAudit, CategoryRequest} from "./category.data";
import {ErrorService} from "../../../core/utils/error.service";
import {catchError} from "rxjs/operators";



@Injectable()
export class CategoryService {

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


  public getCategory(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<Category>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getCategory.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<Category>>(`${this.basePath}/category/${encodeURIComponent(String(id))}`,
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

  public getCategoryAudit(id: number, revisionId: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<CategoryHistoryAudit>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getCategory.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<CategoryHistoryAudit>>(`${this.basePath}/category/audit/id/${encodeURIComponent(String(id))}/revision/${encodeURIComponent(String(revisionId))}`,
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

  public getCategoryRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<CategoryRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getCategory.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<CategoryRequest>>(`${this.basePath}/category/request/${encodeURIComponent(String(id))}`,
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


  public getCategoryAudits(id: number, pageRequest?: PageRequest, categoryAuditsFilter?: { [key: string]: object; }, observe: any = 'body', reportProgress: boolean = false, options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<CategoryHistoryAudit>>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getCategoryAudits.');
    }

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (categoryAuditsFilter !== undefined && categoryAuditsFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>categoryAuditsFilter, 'categoryAuditsFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<CategoryHistoryAudit>>>(`${this.basePath}/category/audit/id/${encodeURIComponent(String(id))}`,
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

  public getCategories( pageRequest?: PageRequest, categoryFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<Category>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (categoryFilter !== undefined && categoryFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>categoryFilter, 'categoryFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<Category>>>(`${this.basePath}/category`,
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

  public getCategoryRequests( pageRequest?: PageRequest, categoryRequestFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<CategoryRequest>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (categoryRequestFilter !== undefined && categoryRequestFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>categoryRequestFilter, 'categoryRequestFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<CategoryRequest>>>(`${this.basePath}/category/request`,
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

  public createCategoryRequest(categoryBody: Category, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (categoryBody === null || categoryBody === undefined) {
      throw new Error('Required parameter categoryBody was null or undefined when calling createCategoryRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/category/request/create`,
      categoryBody,
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

  public editCategoryRequest(categoryBody: Category, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (categoryBody === null || categoryBody === undefined) {
      throw new Error('Required parameter categoryBody was null or undefined when calling createCategoryRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/category/request/modify`,
      categoryBody,
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

  public approveCategoryRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<CategoryRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getCategory.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<CategoryRequest>>(`${this.basePath}/category/request/approve/${encodeURIComponent(String(id))}`,
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

  public rejectCategoryRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<CategoryRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getCategory.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<CategoryRequest>>(`${this.basePath}/category/request/reject/${encodeURIComponent(String(id))}`,
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
