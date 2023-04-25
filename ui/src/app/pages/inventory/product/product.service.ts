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
import {Product, ProductAudit, ProductHistoryAudit, ProductRequest} from "./product.data";
import {ErrorService} from "../../../core/utils/error.service";
import {catchError} from "rxjs/operators";



@Injectable()
export class ProductService {

  protected basePath = null;
  private path = null;
  public defaultHeaders = new HttpHeaders();
  public encoder: HttpParameterCodec = new CustomHttpParameterCodec();

  constructor(private errorService: ErrorService, protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string) {
    this.path = basePath;
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


  public getProduct(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<Product>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getProduct.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<Product>>(`${this.basePath}/product/${encodeURIComponent(String(id))}`,
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

  public deleteProduct(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<Product>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getProduct.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<Product>>(`${this.basePath}/product/delete/${encodeURIComponent(String(id))}`,
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

  public getProductAttachment(url: string, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<any> {
    let headers = this.defaultHeaders;
    return this.httpClient.get(`${this.path}${url}`,
      {
        responseType: 'blob',
        headers: headers,
        observe: observe
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }

  public getProductAudit(id: number, revisionId: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<ProductHistoryAudit>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getProduct.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<ProductHistoryAudit>>(`${this.basePath}/product/audit/id/${encodeURIComponent(String(id))}/revision/${encodeURIComponent(String(revisionId))}`,
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

  public getProductRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<ProductRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getProduct.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<ProductRequest>>(`${this.basePath}/product/request/${encodeURIComponent(String(id))}`,
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


  public getProductAudits(id: number, pageRequest?: PageRequest, productAuditsFilter?: { [key: string]: object; }, observe: any = 'body', reportProgress: boolean = false, options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<ProductHistoryAudit>>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getProductAudits.');
    }

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (productAuditsFilter !== undefined && productAuditsFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>productAuditsFilter, 'productAuditsFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<ProductHistoryAudit>>>(`${this.basePath}/product/audit/id/${encodeURIComponent(String(id))}`,
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

  public getProducts( pageRequest?: PageRequest, productFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<Product>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (productFilter !== undefined && productFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>productFilter, 'productFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<Product>>>(`${this.basePath}/product`,
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

  public getProductRequests( pageRequest?: PageRequest, productRequestFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<ProductRequest>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (productRequestFilter !== undefined && productRequestFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>productRequestFilter, 'productRequestFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<ProductRequest>>>(`${this.basePath}/product/request`,
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

  public createProductRequest(productBody: Product, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (productBody === null || productBody === undefined) {
      throw new Error('Required parameter productBody was null or undefined when calling createProductRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/product/request/create`,
      productBody,
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

  public editProductRequest(productBody: Product, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (productBody === null || productBody === undefined) {
      throw new Error('Required parameter productBody was null or undefined when calling createProductRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/product/request/modify`,
      productBody,
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

  public approveProductRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<ProductRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getProduct.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<ProductRequest>>(`${this.basePath}/product/request/approve/${encodeURIComponent(String(id))}`,
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

  public rejectProductRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<ProductRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getProduct.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<ProductRequest>>(`${this.basePath}/product/request/reject/${encodeURIComponent(String(id))}`,
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
