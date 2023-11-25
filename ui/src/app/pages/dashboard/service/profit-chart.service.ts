import {Inject, Injectable, Optional} from '@angular/core';
import { PeriodsService } from './periods.service';
import { ProfitChart, ProfitChartData } from '../data/profit-chart';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from "@angular/common/http";
import {ErrorService} from "../../../core/utils/error.service";
import {BASE_PATH} from "../../../app.module";
import {Observable, throwError} from "rxjs";
import {catchError, map} from "rxjs/operators";
import {OrderProfitChartSummary} from "../data/orders-profit-chart";

@Injectable()
export class ProfitChartService extends ProfitChartData {

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

  public getProductPeriodStatus(): Observable<HttpEvent<any>> {
    return this.httpClient.get<any>(`${this.basePath}/dashboard/product-period-status-count`,
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

  public getProductSummary(): Observable<HttpEvent<any>> {
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

  private getDataPeriod(data): ProfitChart {
    return {
      chartLabel: data.period,
      data: [data.active, data.inactive, data.total, ],
    };
  }

  getProfitChartData(period: string): Observable<ProfitChart> {
    return this.getProductPeriodStatus().pipe(
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

  getProductsSummaryChartData(): Observable<OrderProfitChartSummary[]> {
    return this.getProductSummary().pipe(map((value: any) => value['product']))
  }
}
