import {Component} from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {DEFAULT_PAGE_REQUEST, GenericResponse} from "../../../core/utils/template/http-util";
import {ReportService} from "../report.service";
import {Observable} from "rxjs";
import {SettingService} from "../../performance/setting/setting.service";
import {PERFORMANCE_STAFF_HEADER, StaffPerformance} from "../../performance/setting/setting.data";

@Component({
  selector: 'report-staff-performance',
  styleUrls: ['./report-staff-performance.component.scss'],
  templateUrl: './report-staff-performance.component.html',
})
export class ReportStaffPerformanceComponent {

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
    columns: PERFORMANCE_STAFF_HEADER
  };

  filterEntity: any = {};

  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private performanceStaffService: SettingService, private reportService: ReportService) {
    this.fetchData({}, true);
  }

  private fetchData(filter: any = {}, startup: boolean = false): void {
    this.performanceStaffService.getPerformanceStaffs()
      .subscribe(((p: object) => {
        const response: GenericResponse<StaffPerformance[]> = p as GenericResponse<StaffPerformance[]>;
        this.source.load(response.data);
        this.dataPresent = true;
        if (!startup && !!response.data && response.data.length > 0) this.downloadStaffPerformanceReport(filter);
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if (!this.filterEntity.name) delete this.filterEntity.name
    if (!this.filterEntity.id) delete this.filterEntity.id
    if (!this.filterEntity.purchaseOrderId) delete this.filterEntity.purchaseOrderId
    if (!this.filterEntity.status) delete this.filterEntity.status
    this.fetchData(this.filterEntity);
  }

  private downloadStaffPerformanceReport(filter: any = {}): void {
    let blob: Observable<any> = this.reportService.downloadFilteredReport(DEFAULT_PAGE_REQUEST, this.format, 'performance-staff', filter);
    this.reportService.blobDownload(blob, 'List_of_staff_performance');
  }

  onSelectionChange(data): void {
    this.format = data;
  }

}
