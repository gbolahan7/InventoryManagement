import {Inject, Injectable, Optional} from '@angular/core';
import { PeriodsService } from './periods.service';
import { OrdersChart, OrdersChartData } from '../data/orders-chart';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from "@angular/common/http";
import {ErrorService} from "../../../core/utils/error.service";
import {BASE_PATH} from "../../../app.module";
import {Observable, throwError} from "rxjs";
import {catchError, map} from "rxjs/operators";
import {OrderProfitChartSummary} from "../data/orders-profit-chart";

@Injectable()
export class OrdersChartService extends OrdersChartData {

  protected basePath = null;
  private path = null;
  public defaultHeaders = new HttpHeaders();


  constructor(private period: PeriodsService, private errorService: ErrorService,
              protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string) {
    super();
    this.path = basePath;
    this.basePath = basePath + '/api/v1';
    this.defaultHeaders.set('Accept', 'application/json');
  }

  public getOrderPeriodStatus(): Observable<HttpEvent<any>> {
    return this.httpClient.get<any>(`${this.basePath}/dashboard/order-period-status-count`,
      {
        responseType: 'json',
        headers: this.defaultHeaders,
        observe: ('body' as any)
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }

  public getOrderSummary(): Observable<HttpEvent<any>> {
    return this.httpClient.get<any>(`${this.basePath}/dashboard/product-order-summary-count`,
      {
        responseType: 'json',
        headers: this.defaultHeaders,
        observe: ('body' as any)
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.handleHttpResponseError(error);
        return throwError(error);
      })
    );
  }


  private getDataPeriod(data): OrdersChart {
    return {
      chartLabel: data.period,
      linesData: [data.total, data.pending, data.paid,  ],
    };
  }

  getOrdersChartData(period: string): Observable<OrdersChart> {
     return this.getOrderPeriodStatus().pipe(
      map((value: any) => {
        let valueData = value.levelMap;
        return {
          week: this.getDataPeriod(valueData.week),
          month: this.getDataPeriod(valueData.month),
          year: this.getDataPeriod(valueData.year),
        };
      }),
      map((value: any) => {
        return value[period]
      })
    );
  }

  getOrdersSummaryChartData(): Observable<OrderProfitChartSummary[]> {
    return this.getOrderSummary().pipe(map((value: any) => value['order']))
  }
}
