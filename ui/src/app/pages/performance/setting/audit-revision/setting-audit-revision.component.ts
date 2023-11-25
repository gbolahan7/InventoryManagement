import {Component} from '@angular/core';
import {PerformanceSettingHistoryAudit,} from "../setting.data";
import {SettingService} from "../setting.service";
import {ActivatedRoute, Params} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'performance-setting-audit-revision',
  styleUrls: ['./setting-audit-revision.component.scss'],
  templateUrl: './setting-audit-revision.component.html',
})
export class SettingAuditRevisionComponent {

  performanceSettingAudit: PerformanceSettingHistoryAudit = null;
  dataPresent: boolean = false;

  constructor(private performanceSettingService: SettingService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id, params.revisionId);
    });
  }

  private fetchData(id: number, revisionId): void {
    this.performanceSettingService.getPerformanceSettingAudit(id, revisionId)
      .subscribe(((p: object) => {
        const response: GenericResponse<PerformanceSettingHistoryAudit> = p as GenericResponse<PerformanceSettingHistoryAudit>;
        this.performanceSettingAudit = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


}
