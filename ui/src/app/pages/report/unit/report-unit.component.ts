import {Component} from '@angular/core';
import {Unit, UNIT_HEADER} from "../../inventory/unit/unit.data";
import {LocalDataSource} from "ng2-smart-table";
import {UnitService} from "../../inventory/unit/unit.service";
import {Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../core/utils/template/http-util";
import {ReportService} from "../report.service";
import {Observable} from "rxjs";

@Component({
  selector: 'report-unit',
  styleUrls: ['./report-unit.component.scss'],
  templateUrl: './report-unit.component.html',
})
export class ReportUnitComponent {

  formats = [{name: 'Pdf', value: 'pdf'}, {name: 'Excel', value: 'xlsx'}];
  format = 'pdf';
  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: false,
    edit: '',
    delete: '',
    columns: UNIT_HEADER
  };

  filterEntity: any = {};

  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private unitService: UnitService, private reportService: ReportService) {
    this.fetchData({}, true);
  }

  private fetchData(filter: any = {}, startup: boolean = false): void {
    this.unitService.getUnits(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<Unit>> = p as GenericResponse<PageResponse<Unit>>;
        this.source.load(response.data.content);
        this.dataPresent = true;
        if(!startup && !!response.data.content && response.data.content.length > 0) this.downloadUnitReport(filter);
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if (!this.filterEntity.name) delete this.filterEntity.name
    if (!this.filterEntity.id) delete this.filterEntity.id
    if (!this.filterEntity.status) delete this.filterEntity.status
    this.fetchData(this.filterEntity);
  }

  private downloadUnitReport(filter: any = {}): void {
    let blob: Observable<any> = this.reportService.downloadFilteredReport(DEFAULT_PAGE_REQUEST, this.format, 'unit', filter);
    this.reportService.blobDownload(blob, 'List_of_unit');
  }

  onSelectionChange(data): void {
    this.format = data;
  }


}
