import { Component } from '@angular/core';
import {
  UNIT_REQUEST_ADD,
  UNIT_REQUEST_DELETE,
  UNIT_REQUEST_HEADER,
  UNIT_REQUEST_MODIFY,
  UnitRequest
} from "../unit.data";
import {LocalDataSource} from "ng2-smart-table";
import {UnitService} from "../unit.service";
import {Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-unit-request-list',
  styleUrls: ['./unit-request-list.component.scss'],
  templateUrl: './unit-request-list.component.html',
})
export class UnitRequestListComponent {

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: UNIT_REQUEST_ADD,
    edit: UNIT_REQUEST_MODIFY,
    delete: UNIT_REQUEST_DELETE,
    columns: UNIT_REQUEST_HEADER
  };

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private unitService: UnitService, private router:Router) {
    this.fetchData();
  }


  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/unit/request', event.data.requestId]);
  }


  private fetchData(filter: any = {}): void {
    this.unitService.getUnitRequests(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<UnitRequest>> = p as GenericResponse<PageResponse<UnitRequest>>;
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
