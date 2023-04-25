import {Inject, Injectable, Optional} from '@angular/core';
import {of as observableOf, Observable, throwError} from 'rxjs';
import { PeriodsService } from './periods.service';
import { TrafficBarData, TrafficBar } from '../data/traffic-bar';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from "@angular/common/http";
import {ErrorService} from "../../../core/utils/error.service";
import {BASE_PATH} from "../../../app.module";
import {catchError, map} from "rxjs/operators";

@Injectable()
export class TrafficBarService extends TrafficBarData {

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

  public getPeriodCount(): Observable<HttpEvent<any>> {
    return this.httpClient.get<any>(`${this.basePath}/dashboard/purchase-order-period-count`,
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

  getDataForWeekPeriod(data: number[]): TrafficBar {
    return {
      data: data,
      labels: this.period.getWeeks(),
      formatter: '{c0} Orders',
    };
  }

  getDataForMonthPeriod(data: number[]): TrafficBar {
    return {
      data: data,
      labels: this.period.getMonths(),
      formatter: '{c0} Orders',
    };
  }

  getDataForYearPeriod(data: number[]): TrafficBar {
    return {
      data: data,
      labels: this.period.getYears(),
      formatter: '{c0} Orders',
    };
  }

  getTrafficBarData(period: string): Observable<TrafficBar> {
    return this.getPeriodCount().pipe(
      map(value => {
        let weekData = this.period.getWeeks().map(v => value[0].levelMap[v].current);
        let monthData = this.period.getMonths().map(v => value[1].levelMap[v].current);
        let yearData = this.period.getYears().map(v => value[2].levelMap[v].current);
        return {
          week: this.getDataForWeekPeriod(weekData),
          month: this.getDataForMonthPeriod(monthData),
          year: this.getDataForYearPeriod(yearData),
        };
      }),
      map((value: any) => {
        return value[period]
      })
    )
  }
}
