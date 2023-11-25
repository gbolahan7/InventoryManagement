import { Component } from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {SettingService} from "../setting.service";
import {Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {
  PerformanceSetting_REQUEST_ADD,
  PerformanceSetting_REQUEST_DELETE, PerformanceSetting_REQUEST_HEADER,
  PerformanceSetting_REQUEST_MODIFY, PerformanceSettingRequest
} from "../setting.data";

@Component({
  selector: 'performance-setting-request-list',
  styleUrls: ['./setting-request-list.component.scss'],
  templateUrl: './setting-request-list.component.html',
})
export class SettingRequestListComponent {

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: PerformanceSetting_REQUEST_ADD,
    edit: PerformanceSetting_REQUEST_MODIFY,
    delete: PerformanceSetting_REQUEST_DELETE,
    columns: PerformanceSetting_REQUEST_HEADER
  };

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private performanceSettingService: SettingService, private router:Router) {
    this.fetchData();
  }


  onClickEvent(event): void {
    this.router.navigate(['pages/performance/setting/request', event.data.requestId]);
  }


  private fetchData(filter: any = {}): void {
    this.performanceSettingService.getPerformanceSettingRequests(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<PerformanceSettingRequest>> = p as GenericResponse<PageResponse<PerformanceSettingRequest>>;
        this.source.load(response.data.content);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if(!this.filterEntity.name) delete this.filterEntity.name
    if(!this.filterEntity.requestStatus) delete this.filterEntity.requestStatus
    if(!this.filterEntity.requestType) delete this.filterEntity.requestType
    this.fetchData(this.filterEntity);
  }

}
