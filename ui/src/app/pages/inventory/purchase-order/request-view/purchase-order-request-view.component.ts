import { Component } from '@angular/core';
import {PurchaseOrderService} from "../purchase-order.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {PurchaseOrder, PurchaseOrderAudit, PurchaseOrderRequest} from "../purchase-order.data";

@Component({
  selector: 'inventory-purchase-order-request-view',
  styleUrls: ['./purchase-order-request-view.component.scss'],
  templateUrl: './purchase-order-request-view.component.html',
})
export class PurchaseOrderRequestViewComponent {

  purchaseOrderRequest: PurchaseOrderRequest = null;
  dataPresent: boolean  = false;

  APPROVE: string = 'Approve';
  REJECT: string = 'Reject'

  constructor(private purchaseOrderService: PurchaseOrderService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchDataRequest(params.id)
    });
  }

  private fetchDataRequest(id: number): void {
    this.purchaseOrderService.getPurchaseOrderRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PurchaseOrderRequest> = p as GenericResponse<PurchaseOrderRequest>;
        this.purchaseOrderRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  onPermission(event, operation: string): void {
    if(operation === this.APPROVE) {
      this.approveDataRequest(this.purchaseOrderRequest.requestId);
    }else if(operation == this.REJECT) {
      this.rejectDataRequest(this.purchaseOrderRequest.requestId);
    }
  }

  private approveDataRequest(id: number): void {
    this.purchaseOrderService.approvePurchaseOrderRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PurchaseOrderRequest> = p as GenericResponse<PurchaseOrderRequest>;
        this.purchaseOrderRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  private rejectDataRequest(id: number): void {
    this.purchaseOrderService.rejectPurchaseOrderRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PurchaseOrderRequest> = p as GenericResponse<PurchaseOrderRequest>;
        this.purchaseOrderRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
