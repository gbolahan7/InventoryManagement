import {Component, OnInit} from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {
  PurchaseOrder,
  PurchaseOrder_CUSTOM, PurchaseOrder_DELETE,
  PurchaseOrder_HEADER,
} from "../purchase-order.data";
import {PurchaseOrderService} from "../purchase-order.service";
import {
  DEFAULT_PAGE_REQUEST,
  GenericResponse,
  PageResponse
} from "../../../../core/utils/template/http-util";
import {Router} from "@angular/router";
import {NbAccessChecker} from "@nebular/security";
import {ConfirmationDialogService} from "../../../../core/utils/confirm-dialog.service";

@Component({
  selector: 'inventory-purchase-order-list',
  styleUrls: ['./purchase-order-list.component.scss'],
  templateUrl: './purchase-order-list.component.html',
})
export class PurchaseOrderListComponent {

  settingsDelete = true;
  settings = this.fetchSettings();

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private purchaseOrderService: PurchaseOrderService, private router:Router, private accessChecker: NbAccessChecker,
              private confirmationService: ConfirmationDialogService) {
    accessChecker
      .isGranted('purchase_order', 'delete')
      .subscribe((value: boolean) => {
        if(!value) {
          this.settingsDelete = false;
          this.settings = this.fetchSettings();
        }
      });
    this.fetchData();
  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/purchase-order/list', event.data.id]);
  }


  private fetchData(filter: any = {}): void {
    this.purchaseOrderService.getPurchaseOrders(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
          const response: GenericResponse<PageResponse<PurchaseOrder>> = p as GenericResponse<PageResponse<PurchaseOrder>>;
          this.source.load(response.data.content);
          this.dataPresent = true;
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if(!this.filterEntity.name) delete this.filterEntity.name
    if(!this.filterEntity.id) delete this.filterEntity.id
    if(!this.filterEntity.status) delete this.filterEntity.status
    this.fetchData(this.filterEntity);
  }

  fetchSettings() {
    return {
      hideSubHeader: true,
      actions: {
        position: 'right',
        delete: this.settingsDelete,
        custom: PurchaseOrder_CUSTOM,
      },
      add: false,
      edit: '',
      delete: PurchaseOrder_DELETE,
      columns: PurchaseOrder_HEADER
    }
  }

  onDeleteConfirm(event): void {
    this.confirmationService.confirm('Delete Purchase Order', 'Are you sure you want to delete?')
      .then((confirmed: boolean) => {
        if (confirmed) {
          let value: PurchaseOrder = event.data;
          this.purchaseOrderService.deletePurchaseOrder(value.id).subscribe((event.confirm.resolve()), event.confirm.reject());
        } else {
          event.confirm.reject();
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  onCustomAction(event) {
    switch ( event.action) {
      case PurchaseOrder_CUSTOM[0].name: //edit
        this.router.navigate(['pages/inventory/purchase-order/edit', event.data.id]);
        break;
    }
  }

}
