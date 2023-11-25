import { Component } from '@angular/core';
import {UnitService} from "../unit.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Unit, UnitAudit, UnitRequest} from "../unit.data";

@Component({
  selector: 'inventory-unit-request-view',
  styleUrls: ['./unit-request-view.component.scss'],
  templateUrl: './unit-request-view.component.html',
})
export class UnitRequestViewComponent {

  unitRequest: UnitRequest = null;
  dataPresent: boolean  = false;

  APPROVE: string = 'Approve';
  REJECT: string = 'Reject'

  constructor(private unitService: UnitService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchDataRequest(params.id)
    });
  }

  private fetchDataRequest(id: number): void {
    this.unitService.getUnitRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<UnitRequest> = p as GenericResponse<UnitRequest>;
        this.unitRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  onPermission(event, operation: string): void {
    if(operation === this.APPROVE) {
      this.approveDataRequest(this.unitRequest.requestId);
    }else if(operation == this.REJECT) {
      this.rejectDataRequest(this.unitRequest.requestId);
    }
  }

  private approveDataRequest(id: number): void {
    this.unitService.approveUnitRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<UnitRequest> = p as GenericResponse<UnitRequest>;
        this.unitRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  private rejectDataRequest(id: number): void {
    this.unitService.rejectUnitRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<UnitRequest> = p as GenericResponse<UnitRequest>;
        this.unitRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
