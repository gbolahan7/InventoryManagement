import {Component, Input, OnInit} from '@angular/core';
import {
  PurchaseOrderHistoryAudit,
} from "../purchase-order.data";
import {PurchaseOrderService} from "../purchase-order.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-purchase-order-audit-revision',
  styleUrls: ['./purchase-order-audit-revision.component.scss'],
  templateUrl: './purchase-order-audit-revision.component.html',
})
export class PurchaseOrderAuditRevisionComponent {

  purchaseOrderAudit: PurchaseOrderHistoryAudit = null;
  dataPresent: boolean  = false;

  constructor(private purchaseOrderService: PurchaseOrderService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id, params.revisionId);
    });
  }

  private fetchData(id: number, revisionId): void {
    this.purchaseOrderService.getPurchaseOrderAudit(id, revisionId)
      .subscribe(((p: object) => {
        const response: GenericResponse<PurchaseOrderHistoryAudit> = p as GenericResponse<PurchaseOrderHistoryAudit>;
        this.purchaseOrderAudit = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


}
