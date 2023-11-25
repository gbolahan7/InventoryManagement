import {Component} from '@angular/core';
import {PurchaseOrderService} from "../purchase-order.service";
import {ActivatedRoute, Params} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";
import {PurchaseOrder} from "../purchase-order.data";
import {LocalDataSource} from "ng2-smart-table";
import {CustomInputImageComponent} from "../../../../core/utils/component/custom-input-image.component";

@Component({
  selector: 'inventory-purchase-order-view',
  styleUrls: ['./purchase-order-view.component.scss'],
  templateUrl: './purchase-order-view.component.html',
})
export class PurchaseOrderViewComponent {

  purchaseOrder: PurchaseOrder = null;
  dataPresent: boolean = false;
  itemSettings = this.getItemTable();

  itemSource: LocalDataSource = new LocalDataSource();

  constructor(private purchaseOrderService: PurchaseOrderService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
    });
  }

  private fetchData(id: number): void {
    this.purchaseOrderService.getPurchaseOrder(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PurchaseOrder> = p as GenericResponse<PurchaseOrder>;
        this.purchaseOrder = response.data;
        this.itemSource.load(this.purchaseOrder.items);
        this.getItemTable();
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
          type: 'string',
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
          type: 'boolean',
          filter: false,
          editable: false,
          addable: false,
        },
      }
    };
  }

}
