import { Component } from '@angular/core';
import {UnitService} from "../unit.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Unit, UnitAudit, UnitRequest} from "../unit.data";
import {NbTagComponent} from "@nebular/theme";

@Component({
  selector: 'inventory-unit-create',
  styleUrls: ['./unit-create.component.scss'],
  templateUrl: './unit-create.component.html',
})
export class UnitCreateComponent {

  unit: Unit = new Unit();
  dataPresent: boolean  = false;
  statuses = [{name: 'Active', key: 'Active'},{name: 'Inactive', key: 'Inactive'}];

  constructor(private unitService: UnitService, private router:Router) {
    this.unit.status = this.statuses[0].name;
  }

  create(): void {
    if(this.unit &&
      !!this.unit.name &&
      !!this.unit.description && !!this.unit.status ) {
      this.createUnit(this.unit);
    }

  }

  private createUnit(payload: any = {}): void {
    this.unitService.createUnitRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/inventory/unit/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
