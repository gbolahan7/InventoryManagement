import {Component} from '@angular/core';
import {SettingService} from "../setting.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";
import {PerformanceSetting, PerformanceSettingRequest} from "../setting.data";

@Component({
  selector: 'performance-setting-edit',
  styleUrls: ['./setting-edit.component.scss'],
  templateUrl: './setting-edit.component.html',
})
export class SettingEditComponent {

  performanceSetting: PerformanceSettingRequest = new PerformanceSettingRequest();
  dataPresent: boolean = false;
  periods = [{name: 'Second', key: 'SECOND'}, {name: 'Minute', key: 'MINUTE'}, {name: 'Hour', key: 'HOUR'},
    {name: 'Day', key: 'DAY'}, {name: 'Week', key: 'WEEK'}, {name: 'Month', key: 'MONTH'}, {name: 'Year', key: 'YEAR'}];

  updateTypes = [{name: 'Inline', key: 'inline'}, {name: 'Aggregate', key: 'aggregate'}];

  constructor(private settingService: SettingService, private router: Router, private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
    });
  }

  private fetchData(id: number): void {
    this.settingService.getPerformanceSetting(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PerformanceSetting> = p as GenericResponse<PerformanceSetting>;
        this.performanceSetting = this.mapFromSettingToSettingRequest(response.data);
        this.performanceSetting.staffStartTime = !!this.performanceSetting.staffStartTime ?  new Date(this.performanceSetting.staffStartTime) : null;
        this.performanceSetting.staffStopTime = !!this.performanceSetting.staffStopTime ?  new Date(this.performanceSetting.staffStopTime) : null;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


  mapFromSettingToSettingRequest(setting: PerformanceSetting): PerformanceSettingRequest {
    let settingRequest: PerformanceSettingRequest = JSON.parse(JSON.stringify(setting))
    settingRequest.performanceSettingId = setting.id;
    return settingRequest;
  }

  edit(): void {
    if (this.performanceSetting &&
      !!this.performanceSetting.name) {
      this.editPerformanceSetting(this.performanceSetting);
    }
  }

  private editPerformanceSetting(payload: any = {}): void {
    this.settingService.editPerformanceSettingRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/performance/setting/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
