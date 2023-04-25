import {Inject, Injectable, Optional} from '@angular/core';
import {of as observableOf, Observable, throwError} from 'rxjs';
import { StatsBarData } from '../data/stats-bar';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from "@angular/common/http";
import {ErrorService} from "../../../core/utils/error.service";
import {BASE_PATH} from "../../../app.module";
import {catchError} from "rxjs/operators";

@Injectable()
export class StatsBarService extends StatsBarData {

  protected basePath = null;
  private path = null;
  public defaultHeaders = new HttpHeaders();
  private data: any;

  constructor(private errorService: ErrorService, protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string) {
    super();
    this.path = basePath;
    this.basePath = basePath + '/api/v1';
    this.defaultHeaders.set('Accept', 'application/json');
    this.data = this.getStatsBar() ;
  }

  public getStatsBar(): Observable<HttpEvent<{ activePeriodDate: string; activeFirstPeriod: number;previousPeriodDate: string; previousFirstPeriod: number; line: number[]; }>> {
    return this.httpClient.get<{ activePeriodDate: string; activeFirstPeriod: number;previousPeriodDate: string; previousFirstPeriod: number; line: number[]; }>(`${this.basePath}/dashboard/profit-stat-card`,
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


  getStatsBarData(): Observable<{ activePeriodDate: string; activeFirstPeriod: number;previousPeriodDate: string; previousFirstPeriod: number; line: number[]; }> {
    return this.data;
  }

}
