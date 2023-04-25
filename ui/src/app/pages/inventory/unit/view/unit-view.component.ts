import { Component } from '@angular/core';
import {UnitService} from "../unit.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Unit, UnitAudit, UnitRequest} from "../unit.data";

@Component({
  selector: 'inventory-unit-view',
  styleUrls: ['./unit-view.component.scss'],
  templateUrl: './unit-view.component.html',
})
export class UnitViewComponent {

  unit: Unit = null;
  dataPresent: boolean  = false;

  constructor(private unitService: UnitService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
    });
  }

  private fetchData(id: number): void {
    this.unitService.getUnit(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<Unit> = p as GenericResponse<Unit>;
        this.unit = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


}
