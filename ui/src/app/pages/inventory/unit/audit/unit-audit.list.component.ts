import {Component, Input, OnInit} from '@angular/core';
import {
  UNIT_AUDIT_ADD, UNIT_AUDIT_DELETE,
  UNIT_AUDIT_HEADER, UNIT_AUDIT_MODIFY,
  UnitHistoryAudit,
} from "../unit.data";
import {LocalDataSource} from "ng2-smart-table";
import {UnitService} from "../unit.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-unit-audit-list',
  styleUrls: ['./unit-audit.list.component.scss'],
  templateUrl: './unit-audit.list.component.html',
})
export class UnitAuditListComponent implements OnInit{

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: UNIT_AUDIT_ADD,
    edit: UNIT_AUDIT_MODIFY,
    delete: UNIT_AUDIT_DELETE,
    columns: UNIT_AUDIT_HEADER
  };

  filterEntity: any = {};
  @Input() entityId: number;


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private unitService: UnitService, private router:Router, private activatedRoute: ActivatedRoute,) {

  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/unit/audit', this.entityId, 'revision', event.data.revisionId]);
  }

  private fetchData(id: number, filter: any = null): void {
    this.unitService.getUnitAudits(id, DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<UnitHistoryAudit>> = p as GenericResponse<PageResponse<UnitHistoryAudit>>;
        let data: UnitHistoryAudit[] = response.data.content;
        this.source.load(data.map(obj => ({ ...obj, name: obj.entity.name })));
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if(!this.filterEntity.revisionId) delete this.filterEntity.revisionId
    if(!this.filterEntity.revisionType) delete this.filterEntity.revisionType
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
