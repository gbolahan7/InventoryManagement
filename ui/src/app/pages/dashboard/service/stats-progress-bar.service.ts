import {Inject, Injectable, Optional} from '@angular/core';
import {of as observableOf, Observable, throwError} from 'rxjs';
import { ProgressInfo, StatsProgressBarData } from '../data/stats-progress-bar';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from "@angular/common/http";
import {PeriodsService} from "./periods.service";
import {ErrorService} from "../../../core/utils/error.service";
import {BASE_PATH} from "../../../app.module";
import {catchError} from "rxjs/operators";

@Injectable()
export class StatsProgressBarService extends StatsProgressBarData {

  protected basePath = null;
  private path = null;
  public defaultHeaders = new HttpHeaders();


  constructor(private errorService: ErrorService,
              protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string) {
    super();
    this.path = basePath;
    this.basePath = basePath + '/api/v1';
    this.defaultHeaders.set('Accept', 'application/json');
  }

  public getProgressInfo(): Observable<any> {
    return this.httpClient.get<any>(`${this.basePath}/dashboard/progress-info`,
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


  getProgressInfoData(): Observable<ProgressInfo[]> {
    return this.getProgressInfo();
  }
}
