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
  PurchaseOrder,
  PurchaseOrderAudit,
  PurchaseOrderHistoryAudit,
  PurchaseOrderItem,
  PurchaseOrderRequest
} from "./purchase-order.data";
import {ErrorService} from "../../../core/utils/error.service";
import {catchError} from "rxjs/operators";
import {Product} from "../product/product.data";



@Injectable()
export class PurchaseOrderService {

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


  public getPurchaseOrder(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PurchaseOrder>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPurchaseOrder.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PurchaseOrder>>(`${this.basePath}/purchase-order/${encodeURIComponent(String(id))}`,
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

  public deletePurchaseOrder(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PurchaseOrder>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPurchaseOrder.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PurchaseOrder>>(`${this.basePath}/purchase-order/delete/${encodeURIComponent(String(id))}`,
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

  public getPurchaseOrderAudit(id: number, revisionId: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PurchaseOrderHistoryAudit>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPurchaseOrder.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PurchaseOrderHistoryAudit>>(`${this.basePath}/purchase-order/audit/id/${encodeURIComponent(String(id))}/revision/${encodeURIComponent(String(revisionId))}`,
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

  public getPurchaseOrderRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PurchaseOrderRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPurchaseOrder.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PurchaseOrderRequest>>(`${this.basePath}/purchase-order/request/${encodeURIComponent(String(id))}`,
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


  public getPurchaseOrderAudits(id: number, pageRequest?: PageRequest, purchaseOrderAuditsFilter?: { [key: string]: object; }, observe: any = 'body', reportProgress: boolean = false, options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<PurchaseOrderHistoryAudit>>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPurchaseOrderAudits.');
    }

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (purchaseOrderAuditsFilter !== undefined && purchaseOrderAuditsFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>purchaseOrderAuditsFilter, 'purchaseOrderAuditsFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<PurchaseOrderHistoryAudit>>>(`${this.basePath}/purchase-order/audit/id/${encodeURIComponent(String(id))}`,
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

  public getPurchaseOrders( pageRequest?: PageRequest, purchaseOrderFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<PurchaseOrder>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (purchaseOrderFilter !== undefined && purchaseOrderFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>purchaseOrderFilter, 'purchaseOrderFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<PurchaseOrder>>>(`${this.basePath}/purchase-order`,
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

  public getPurchaseOrderItems( pageRequest?: PageRequest, purchaseOrderFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<PurchaseOrderItem>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (purchaseOrderFilter !== undefined && purchaseOrderFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>purchaseOrderFilter, 'purchaseOrderItemFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<PurchaseOrderItem>>>(`${this.basePath}/purchase-order/items`,
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

  public getPurchaseOrderRequests( pageRequest?: PageRequest, purchaseOrderRequestFilter?: { [key: string]: object; }, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PageResponse<PurchaseOrderRequest>>>> {

    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (purchaseOrderRequestFilter !== undefined && purchaseOrderRequestFilter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>purchaseOrderRequestFilter, 'purchaseOrderRequestFilter');
    }

    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<PageResponse<PurchaseOrderRequest>>>(`${this.basePath}/purchase-order/request`,
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

  public populatePurchaseOrderItems(purchaseOrderItems: PurchaseOrderItem[], observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<PurchaseOrderItem[]>>> {
    if (purchaseOrderItems === null || purchaseOrderItems === undefined) {
      throw new Error('Required parameter purchaseOrderItems was null or undefined when calling populatePurchaseOrderItems.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<PurchaseOrderItem[]>>(`${this.basePath}/purchase-order/populate/items`,
      purchaseOrderItems,
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

  public createPurchaseOrderRequest(purchaseOrderBody: PurchaseOrder, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (purchaseOrderBody === null || purchaseOrderBody === undefined) {
      throw new Error('Required parameter purchaseOrderBody was null or undefined when calling createPurchaseOrderRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/purchase-order/request/create`,
      purchaseOrderBody,
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

  public editPurchaseOrderRequest(purchaseOrderBody: PurchaseOrder, observe: any = 'body', options?: {httpHeaderAccept?: 'application/json'}): Observable<HttpEvent<GenericResponse<number>>> {
    if (purchaseOrderBody === null || purchaseOrderBody === undefined) {
      throw new Error('Required parameter purchaseOrderBody was null or undefined when calling createPurchaseOrderRequest.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.post<GenericResponse<number>>(`${this.basePath}/purchase-order/request/modify`,
      purchaseOrderBody,
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

  public approvePurchaseOrderRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PurchaseOrderRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPurchaseOrder.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PurchaseOrderRequest>>(`${this.basePath}/purchase-order/request/approve/${encodeURIComponent(String(id))}`,
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

  public rejectPurchaseOrderRequest(id: number, observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<PurchaseOrderRequest>>> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter id was null or undefined when calling getPurchaseOrder.');
    }
    let headers = this.defaultHeaders;
    return this.httpClient.get<GenericResponse<PurchaseOrderRequest>>(`${this.basePath}/purchase-order/request/reject/${encodeURIComponent(String(id))}`,
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

  public getProducts( observe: any = 'body', options?: { httpHeaderAccept?: 'application/json' }): Observable<HttpEvent<GenericResponse<Product[]>>> {
    let headers = this.defaultHeaders;

    return this.httpClient.get<GenericResponse<Product[]>>(`${this.basePath}/purchase-order/products`,
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
