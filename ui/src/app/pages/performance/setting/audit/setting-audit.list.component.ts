import {Component, Input, OnInit} from '@angular/core';
import {
  PerformanceSetting_AUDIT_ADD,
  PerformanceSetting_AUDIT_DELETE,
  PerformanceSetting_AUDIT_HEADER,
  PerformanceSetting_AUDIT_MODIFY,
  PerformanceSettingHistoryAudit,
} from "../setting.data";
import {LocalDataSource} from "ng2-smart-table";
import {SettingService} from "../setting.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'performance-setting-audit-list',
  styleUrls: ['./setting-audit.list.component.scss'],
  templateUrl: './setting-audit.list.component.html',
})
export class SettingAuditListComponent implements OnInit {

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: PerformanceSetting_AUDIT_ADD,
    edit: PerformanceSetting_AUDIT_MODIFY,
    delete: PerformanceSetting_AUDIT_DELETE,
    columns: PerformanceSetting_AUDIT_HEADER
  };

  filterEntity: any = {};
  @Input() entityId: number;


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private performanceSettingService: SettingService, private router: Router, private activatedRoute: ActivatedRoute,) {

  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/performance/setting/audit', this.entityId, 'revision', event.data.revisionId]);
  }

  private fetchData(id: number, filter: any = null): void {
    this.performanceSettingService.getPerformanceSettingAudits(id, DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<PerformanceSettingHistoryAudit>> = p as GenericResponse<PageResponse<PerformanceSettingHistoryAudit>>;
        let data: PerformanceSettingHistoryAudit[] = response.data.content;
        this.source.load(data.map(obj => ({...obj, name: obj.entity.name})));
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if (!this.filterEntity.revisionId) delete this.filterEntity.revisionId
    if (!this.filterEntity.revisionType) delete this.filterEntity.revisionType
    this.fetchData(this.entityId, this.filterEntity);
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: Params) => {
      let id: number = !!params.id ? params.id : this.entityId;
      this.fetchData(id);
      this.entityId = id;
    });
  }

}
