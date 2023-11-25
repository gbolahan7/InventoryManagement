import {Component} from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {GenericResponse} from "../../../core/utils/template/http-util";
import {SettingService} from "../setting/setting.service";
import {PERFORMANCE_STAFF_HEADER, StaffPerformance} from "../setting/setting.data";

@Component({
  selector: 'performance-staff',
  styleUrls: ['./performance-staff.component.scss'],
  templateUrl: './performance-staff.component.html',
})
export class PerformanceStaffComponent {

  settings = {
    actions: { add: false, edit: false, delete: false },
    columns: PERFORMANCE_STAFF_HEADER
  };

  filterEntity: any = {};

  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private performanceStaffService: SettingService) {
    this.fetchData();
  }

  private fetchData(): void {
    this.performanceStaffService.getPerformanceStaffs()
      .subscribe(((p: object) => {
        const response: GenericResponse<StaffPerformance[]> = p as GenericResponse<StaffPerformance[]>;
        this.source.load(response.data);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


}
