import {Component} from '@angular/core';
import {SettingService} from "../setting.service";
import {ActivatedRoute, Params} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";
import {PerformanceSettingRequest} from "../setting.data";

@Component({
  selector: 'performance-setting-request-view',
  styleUrls: ['./setting-request-view.component.scss'],
  templateUrl: './setting-request-view.component.html',
})
export class SettingRequestViewComponent {

  settingRequest: PerformanceSettingRequest = null;
  dataPresent: boolean = false;

  APPROVE: string = 'Approve';
  REJECT: string = 'Reject'

  constructor(private settingService: SettingService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchDataRequest(params.id)
    });
  }

  private fetchDataRequest(id: number): void {
    this.settingService.getPerformanceSettingRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PerformanceSettingRequest> = p as GenericResponse<PerformanceSettingRequest>;
        this.settingRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  onPermission(event, operation: string): void {
    if (operation === this.APPROVE) {
      this.approveDataRequest(this.settingRequest.requestId);
    } else if (operation == this.REJECT) {
      this.rejectDataRequest(this.settingRequest.requestId);
    }
  }

  private approveDataRequest(id: number): void {
    this.settingService.approvePerformanceSettingRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PerformanceSettingRequest> = p as GenericResponse<PerformanceSettingRequest>;
        this.settingRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  private rejectDataRequest(id: number): void {
    this.settingService.rejectPerformanceSettingRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PerformanceSettingRequest> = p as GenericResponse<PerformanceSettingRequest>;
        this.settingRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
