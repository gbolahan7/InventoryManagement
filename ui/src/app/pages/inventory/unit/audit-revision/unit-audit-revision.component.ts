import {Component, Input, OnInit} from '@angular/core';
import {
  UnitHistoryAudit,
} from "../unit.data";
import {UnitService} from "../unit.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-unit-audit-revision',
  styleUrls: ['./unit-audit-revision.component.scss'],
  templateUrl: './unit-audit-revision.component.html',
})
export class UnitAuditRevisionComponent {

  unitAudit: UnitHistoryAudit = null;
  dataPresent: boolean  = false;

  constructor(private unitService: UnitService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id, params.revisionId);
    });
  }

  private fetchData(id: number, revisionId): void {
    this.unitService.getUnitAudit(id, revisionId)
      .subscribe(((p: object) => {
        const response: GenericResponse<UnitHistoryAudit> = p as GenericResponse<UnitHistoryAudit>;
        this.unitAudit = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


}
