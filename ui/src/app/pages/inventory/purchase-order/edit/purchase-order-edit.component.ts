import {Component} from '@angular/core';
import {PurchaseOrderService} from "../purchase-order.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";
import {PurchaseOrder, PurchaseOrderRequest} from "../purchase-order.data";
import {LocalDataSource} from "ng2-smart-table";
import {CustomInputImageComponent} from "../../../../core/utils/component/custom-input-image.component";

@Component({
  selector: 'inventory-purchase-order-edit',
  styleUrls: ['./purchase-order-edit.component.scss'],
  templateUrl: './purchase-order-edit.component.html',
})
export class PurchaseOrderEditComponent {

  purchaseOrder: PurchaseOrderRequest = new PurchaseOrderRequest();
  dataPresent: boolean = false;
  statuses = [{name: 'Paid', key: 'Paid'}, {name: 'Pending', key: 'Pending'}];
  itemSettings = this.getItemTable();
  itemSource: LocalDataSource = new LocalDataSource();


  constructor(private purchaseOrderService: PurchaseOrderService, private router:Router, private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
    });
  }

  private fetchData(id: number): void {
    this.purchaseOrderService.getPurchaseOrder(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PurchaseOrder> = p as GenericResponse<PurchaseOrder>;
        this.purchaseOrder = this.mapFromPurchaseOrderToPurchaseOrderRequest(response.data);
        this.itemSource.load(response.data.items)
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  mapFromPurchaseOrderToPurchaseOrderRequest(purchaseOrder: PurchaseOrder): PurchaseOrderRequest {
    let purchaseOrderRequest: PurchaseOrderRequest = JSON.parse(JSON.stringify(purchaseOrder))
    purchaseOrderRequest.purchaseOrderId = purchaseOrder.id;
    return purchaseOrderRequest;
  }

  edit(): void {
    if(this.purchaseOrder &&
      !!this.purchaseOrder.status ) {
      this.editPurchaseOrder(this.purchaseOrder);
    }

  }

  private editPurchaseOrder(payload: any = {}): void {
    this.purchaseOrderService.editPurchaseOrderRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/inventory/purchase-order/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


  getItemTable(): any {
    return {
      hideSubHeader: true,
      actions: {
        position: 'right',
      },
      add: false,
      edit: '',
      delete: '',
      columns: {
        qrcode: {
          title: 'Scan',
          type: 'custom',
          filter: false,
          editable: false,
          addable: false,
          renderComponent: CustomInputImageComponent
        },
        productName: {
          title: 'Name',
          type: 'html',
          filter: false,
          editable: false,
          addable: false,
        },
        quantity: {
          title: 'Quantity',
          type: 'number',
          filter: false,
          editable: false,
          addable: false,
        },
        productCode: {
          title: 'Code',
          type: 'string',
          filter: false,
          editable: false,
          addable: false,
        },
        amount: {
          title: 'Amount',
          type: 'number',
          filter: false,
          editable: false,
          addable: false,
        },
        vatEnabled: {
          title: 'VAT',
          filter: false,
          editable: false,
          addable: false,
        },
      }
    };
  }

}
