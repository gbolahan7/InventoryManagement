import {Inject, Injectable, Optional} from "@angular/core";
import {
  HttpClient,
  HttpErrorResponse, HttpEvent,
  HttpHeaders,
  HttpParameterCodec,
  HttpParams,
  HttpResponse
} from "@angular/common/http";
import {CustomHttpParameterCodec, PageRequest} from "../../core/utils/template/http-util";
import {ErrorService} from "../../core/utils/error.service";
import {BASE_PATH} from "../../app.module";
import {Observable, throwError} from "rxjs";
import {catchError, take} from "rxjs/operators";


@Injectable()
export class ReportService {

  protected basePath = null;
  public defaultHeaders = new HttpHeaders();
  public encoder: HttpParameterCodec = new CustomHttpParameterCodec();

  constructor(private errorService: ErrorService, protected httpClient: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string) {
    this.basePath = basePath + '/api/v1';
    this.defaultHeaders.set('Accept', 'application/json');
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

  public downloadFilteredReport(pageRequest: PageRequest, format: string, domain: string,
                                filter?: { [key: string]: object; }, observe: any = 'body',
                                options?: { httpHeaderAccept?: 'application/json' }): Observable<Blob> {
    let queryParameters = new HttpParams({encoder: this.encoder});
    if (pageRequest !== undefined && pageRequest !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>pageRequest, 'pageRequest');
    }
    if (filter !== undefined && filter !== null) {
      queryParameters = this.addToHttpParams(queryParameters,
        <any>filter, 'filter');
    }

    let headers = this.defaultHeaders;
    return this.httpClient.get(`${this.basePath}/report/${format}/${domain}`,
      {
        params: queryParameters,
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

  public blobDownload(observableBlob: Observable<Blob>, topic: string = 'Report_List'): void {
    observableBlob
      .pipe(take(1))
      .subscribe((response) => {
        const downloadLink = document.createElement('a');
        downloadLink.href = URL.createObjectURL(response);
        downloadLink.download = this.generateRandomString(topic);
        downloadLink.click();
      });
  }

  private generateRandomString(topic: string): string {
    return topic+ '_'+ String(new Date().getTime())
  }

}
