import { Component } from '@angular/core';
import {
  PurchaseOrder_REQUEST_ADD,
  PurchaseOrder_REQUEST_DELETE,
  PurchaseOrder_REQUEST_HEADER,
  PurchaseOrder_REQUEST_MODIFY,
  PurchaseOrderRequest
} from "../purchase-order.data";
import {LocalDataSource} from "ng2-smart-table";
import {PurchaseOrderService} from "../purchase-order.service";
import {Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-purchase-order-request-list',
  styleUrls: ['./purchase-order-request-list.component.scss'],
  templateUrl: './purchase-order-request-list.component.html',
})
export class PurchaseOrderRequestListComponent {

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: PurchaseOrder_REQUEST_ADD,
    edit: PurchaseOrder_REQUEST_MODIFY,
    delete: PurchaseOrder_REQUEST_DELETE,
    columns: PurchaseOrder_REQUEST_HEADER
  };

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private purchaseOrderService: PurchaseOrderService, private router:Router) {
    this.fetchData();
  }


  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/purchase-order/request', event.data.requestId]);
  }


  private fetchData(filter: any = {}): void {
    this.purchaseOrderService.getPurchaseOrderRequests(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<PurchaseOrderRequest>> = p as GenericResponse<PageResponse<PurchaseOrderRequest>>;
        this.source.load(response.data.content);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if(!this.filterEntity.name) delete this.filterEntity.name
    if(!this.filterEntity.requestStatus) delete this.filterEntity.requestStatus
    if(!this.filterEntity.requestType) delete this.filterEntity.requestType
    this.fetchData(this.filterEntity);
  }

}
