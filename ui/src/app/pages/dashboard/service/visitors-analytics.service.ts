import {Inject, Injectable, Optional} from '@angular/core';
import {of as observableOf, Observable, throwError, of} from 'rxjs';
import { PeriodsService } from './periods.service';
import { OutlineData, VisitorsAnalyticsData } from '../data/visitors-analytics';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from "@angular/common/http";
import {ErrorService} from "../../../core/utils/error.service";
import {BASE_PATH} from "../../../app.module";
import {catchError, map} from "rxjs/operators";

@Injectable()
export class VisitorsAnalyticsService extends VisitorsAnalyticsData {

  protected basePath = null;
  private path = null;
  public defaultHeaders = new HttpHeaders();
  data: Observable<any>;


  constructor(private period: PeriodsService, private errorService: ErrorService,
              protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string) {
    super();
    this.path = basePath;
    this.basePath = basePath + '/api/v1';
    this.defaultHeaders.set('Accept', 'application/json');
    this.data = this.getVisitorAnalytics();
  }

  public getVisitorAnalytics(): Observable<HttpEvent<any>> {
    return this.httpClient.get<any>(`${this.basePath}/dashboard/visitor-analytics`,
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

  getInnerLineChartData(): Observable<number[]> {
    if(!this.data) return of([]);
    return this.data.pipe(map(value => {
      return value.uniqueVisitors;
    }))
  }

  getOutlineLineChartData(): Observable<OutlineData[]> {
    if(!this.data) return of([]);
    return this.data.pipe(map(value => {
      return value.pageViews;
    }))
  }

  getPieChartData(): Observable<{percent: number, value: number}> {
    if(!this.data) return of({percent: 0, value: 0});
    return this.data.pipe(map(value => {
      return {
        percent: value.newVisitorPercent,
        value: value.totalVisitor
      };
    }))
  }
}
