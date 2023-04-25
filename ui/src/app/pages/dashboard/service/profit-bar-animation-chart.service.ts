import {Inject, Injectable, Optional} from '@angular/core';
import {Observable, of as observableOf, throwError} from 'rxjs';
import {ProfitBarAnimationChartData} from '../data/profit-bar-animation-chart';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from "@angular/common/http";
import {ErrorService} from "../../../core/utils/error.service";
import {BASE_PATH} from "../../../app.module";
import {catchError} from "rxjs/operators";

@Injectable()
export class ProfitBarAnimationChartService extends ProfitBarAnimationChartData {

  protected basePath = null;
  private path = null;
  public defaultHeaders = new HttpHeaders();
  private data: any;

  constructor(private errorService: ErrorService, protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string) {
    super();
    this.path = basePath;
    this.basePath = basePath + '/api/v1';
    this.defaultHeaders.set('Accept', 'application/json');
    this.data = this.getProfitLine() ;
  }

  public getProfitLine(): Observable<HttpEvent<{ firstLine: number[]; secondLine: number[]; }>> {
    return this.httpClient.get<{ firstLine: number[]; secondLine: number[]; }>(`${this.basePath}/dashboard/profit-bar`,
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


  getChartData(): Observable<{ firstLine: number[]; secondLine: number[]; }> {
    return this.data
  }
}
