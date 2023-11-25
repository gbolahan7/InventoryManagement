import {Component, OnInit} from '@angular/core';
import {PurchaseOrderService} from "../purchase-order.service";
import {Router} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";
import {PURCHASE_ORDER_ITEM_ADD, PurchaseOrder, PurchaseOrderItem} from "../purchase-order.data";
import {LocalDataSource} from "ng2-smart-table";
import {CustomInputEditorComponent} from "../../../../core/utils/component/custom-input-editor.component";
import {Product} from "../../product/product.data";
import {CustomInputImageComponent} from "../../../../core/utils/component/custom-input-image.component";
import {ConfirmationDialogService} from "../../../../core/utils/confirm-dialog.service";

@Component({
  selector: 'inventory-purchase-order-create',
  styleUrls: ['./purchase-order-create.component.scss'],
  templateUrl: './purchase-order-create.component.html',
})
export class PurchaseOrderCreateComponent implements OnInit {

  purchaseOrder: PurchaseOrder = new PurchaseOrder();
  dataPresent: boolean = false;
  statuses = [{name: 'Paid', key: 'Paid'}, {name: 'Pending', key: 'Pending'}];
  products: ProductTrim[] = [];
  currentItems: PurchaseOrderItem[] = [];
  itemSettings = this.getItemTable();

  itemSource: LocalDataSource = new LocalDataSource();

  constructor(private purchaseOrderService: PurchaseOrderService, private router: Router, private confirmationService: ConfirmationDialogService) {
    this.purchaseOrder.status = this.statuses[0].name;
  }

  create(): void {
    if (this.purchaseOrder &&
      !!this.purchaseOrder.purchasedDate &&
      !!this.purchaseOrder.description &&
      !!this.purchaseOrder.status) {
      if (this.currentItems && this.currentItems.length > 0) this.purchaseOrder.items = this.currentItems;
      this.createPurchaseOrder(this.purchaseOrder);
    }

  }

  private createPurchaseOrder(payload: any = {}): void {
    this.purchaseOrderService.createPurchaseOrderRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/inventory/purchase-order/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  itemDelete(event): void {
    this.confirmationService.confirm('Delete Purchase Order', 'Are you sure you want to delete?')
      .then((confirmed: boolean) => {
        if (confirmed) {
          event.confirm.resolve();
        } else {
          event.confirm.reject();
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  itemCreate(event): void {
    let value: PurchaseOrderItem = event.newData;
    value.productCode = value.productName;
    value.vatEnabled = !(value.vatEnabled === null || value.vatEnabled === undefined || !value.vatEnabled);
    this.currentItems.push(value);
    this.purchaseOrderService.populatePurchaseOrderItems(this.currentItems)
      .subscribe(((p: object) => {
        const response: GenericResponse<PurchaseOrderItem[]> = p as GenericResponse<PurchaseOrderItem[]>;
        this.currentItems = response.data;
        for (let i = 0; i < this.currentItems.length; i++) {
          let item = this.currentItems[i];
          this.products = this.products.filter(product => product.value !== item.productCode)
        }
        this.reloadItemTable();
        event.confirm.resolve(this.currentItems[this.currentItems.length - 1]);
        this.dataPresent = true;
      }), (error) => {
        this.currentItems = this.currentItems.filter(item => item.productCode !== value.productCode)
        event.confirm.reject();
      });
  }

  private fetchProductData(event: any = null): void {
    this.purchaseOrderService.getProducts()
      .subscribe(((p: object) => {
        const response: GenericResponse<Product[]> = p as GenericResponse<Product[]>;
        this.products = response.data.map(value => {
          let product = new ProductTrim();
          product.title = value.name;
          product.value = value.code;
          return product;
        })
        this.reloadItemTable();
        this.dataPresent = true;
        if (!!event) event.confirm.resolve();
      }), (error) => console.log(error));
  }

  ngOnInit(): void {
    this.fetchProductData();
  }


  reloadItemTable(): void {
    this.itemSettings = this.getItemTable();
  }

  getItemTable(): any {
    return {
      hideSubHeader: false,
      actions: {
        position: 'left',
      },
      add: PURCHASE_ORDER_ITEM_ADD,
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
          filter: false,
          type: 'html',
          editor: {
            selectText: 'Select',
            type: 'list',
            config: {
              list: this.products,
            },
          }
        },
        quantity: {
          title: 'Quantity',
          type: 'number',
          filter: false,
          editor: {
            type: 'custom',
            component: CustomInputEditorComponent,
          },
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
          editor: {
            type: 'checkbox',
            config: {
              true: true,
              false: false,
            },
          },
        },
        totalAmount: {
          title: 'Total',
          type: 'number',
          filter: false,
          editable: false,
          addable: false,
        },
      }
    };
  }


}

export class ProductTrim {
  title: string;
  value: string;
}
