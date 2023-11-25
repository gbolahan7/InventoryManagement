import { Component } from '@angular/core';
import {UnitService} from "../unit.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Unit, UnitAudit, UnitRequest} from "../unit.data";
import {NbTagComponent} from "@nebular/theme";

@Component({
  selector: 'inventory-unit-edit',
  styleUrls: ['./unit-edit.component.scss'],
  templateUrl: './unit-edit.component.html',
})
export class UnitEditComponent {

  unit: UnitRequest = new UnitRequest();
  dataPresent: boolean  = false;
  statuses = [{name: 'Active', key: 'Active'},{name: 'Inactive', key: 'Inactive'}];

  constructor(private unitService: UnitService, private router:Router, private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
    });
  }

  private fetchData(id: number): void {
    this.unitService.getUnit(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<Unit> = p as GenericResponse<Unit>;
        this.unit = this.mapFromUnitToUnitRequest(response.data);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  mapFromUnitToUnitRequest(unit: Unit): UnitRequest {
    let unitRequest: UnitRequest = JSON.parse(JSON.stringify(unit))
    unitRequest.unitId = unit.id;
    return unitRequest;
  }

  edit(): void {
    if(this.unit &&
      !!this.unit.name &&
      !!this.unit.description && !!this.unit.status ) {
      this.editUnit(this.unit);
    }

  }

  private editUnit(payload: any = {}): void {
    this.unitService.editUnitRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/inventory/unit/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
