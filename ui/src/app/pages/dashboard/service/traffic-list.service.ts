import {Inject, Injectable, Optional} from '@angular/core';
import {of as observableOf, Observable, throwError} from 'rxjs';
import { PeriodsService } from './periods.service';
import { TrafficList, TrafficListData } from '../data/traffic-list';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from "@angular/common/http";
import {ErrorService} from "../../../core/utils/error.service";
import {BASE_PATH} from "../../../app.module";
import {catchError, filter, map} from "rxjs/operators";

@Injectable()
export class TrafficListService extends TrafficListData {

  protected basePath = null;
  private path = null;
  public defaultHeaders = new HttpHeaders();
  private weekData = null;
  private monthData = null;
  private yearData = null;
  private getRandom = (roundTo: number) => Math.round(Math.random() * roundTo);
  private data = {};


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

  private getDataWeek(): TrafficList[] {
    const getFirstDateInPeriod = () => {
      const weeks = this.period.getWeeks();

      return weeks[weeks.length - 1];
    };

    return this.reduceData('week', this.period.getWeeks(), getFirstDateInPeriod);
  }

  private getDataMonth(): TrafficList[] {
    const getFirstDateInPeriod = () => {
      const months = this.period.getMonths();

      return months[months.length - 1];
    };

    return this.reduceData('month', this.period.getMonths(), getFirstDateInPeriod);
  }

  private getDataYear(): TrafficList[] {
    const getFirstDateInPeriod = () => {
      const years = this.period.getYears();

      return `${parseInt(years[0], 10) - 1}`;
    };

    return this.reduceData('year', this.period.getYears(), getFirstDateInPeriod);
  }

  private reduceData(periodKey: string, timePeriods: string[], getFirstDateInPeriod: () => string): TrafficList[] {
    let prev = this.getRandom(100), cur = this.getRandom(1000), next = this.getRandom(100);
    return timePeriods.reduce((result, timePeriod, index) => {
      if(periodKey === 'week') {
        prev = this.weekData[timePeriod].prev;
        cur = this.weekData[timePeriod].current;
        next = this.weekData[timePeriod].next;
      }else if(periodKey === 'month') {
        prev = this.monthData[timePeriod].prev;
        cur = this.monthData[timePeriod].current;
        next = this.monthData[timePeriod].next;
      } if(periodKey === 'year') {
        prev = this.yearData[timePeriod].prev;
        cur = this.yearData[timePeriod].current;
        next = this.yearData[timePeriod].next;
      }
      const hasResult = result[index - 1];
      const prevDate = hasResult ?
        result[index - 1].comparison.nextDate :
        getFirstDateInPeriod();
      const prevValue = hasResult ?
        result[index - 1].comparison.nextValue : prev;
      const nextValue = next;
      const deltaValue = prevValue - nextValue;

      const item = {
        date: timePeriod,
        value: cur,
        delta: {
          up: deltaValue <= 0,
          value: Math.abs(deltaValue),
        },
        comparison: {
          prevDate,
          prevValue,
          nextDate: timePeriod,
          nextValue,
        },
      };

      return [...result, item];
    }, []);
  }

  getTrafficListData(period: string): Observable<TrafficList> {
    return this.getPeriodCount().pipe(
      map(value => {
        this.weekData = value[0].levelMap;
        this.monthData = value[1].levelMap;
        this.yearData = value[2].levelMap;
        return {
          week: this.getDataWeek(),
          month: this.getDataMonth(),
          year: this.getDataYear(),
        };
      }),
      map((value: any) => {
        return value[period]
      })
    )
  }
}
