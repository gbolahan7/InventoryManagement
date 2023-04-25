import { Component } from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {
  Product,
  PRODUCT_ADD,
  PRODUCT_CUSTOM,
  PRODUCT_DELETE,
  PRODUCT_HEADER,
  PRODUCT_MODIFY
} from "../product.data";
import {ProductService} from "../product.service";
import {
  DEFAULT_PAGE_REQUEST,
  GenericResponse,
  PageResponse
} from "../../../../core/utils/template/http-util";
import {Router} from "@angular/router";
import {NbAccessChecker} from "@nebular/security";
import {ConfirmationDialogService} from "../../../../core/utils/confirm-dialog.service";

@Component({
  selector: 'inventory-product-list',
  styleUrls: ['./product-list.component.scss'],
  templateUrl: './product-list.component.html',
})
export class ProductListComponent {

  settingsDelete = true;
  settings = this.fetchSettings();

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private productService: ProductService, private router:Router, accessChecker: NbAccessChecker, private confirmationService: ConfirmationDialogService) {
    accessChecker
      .isGranted('product', 'delete')
      .subscribe((value: boolean) => {
        if(!value) {
          this.settingsDelete = false;
          this.settings = this.fetchSettings();
        }
      });
    this.fetchData();
  }

  onDeleteConfirm(event): void {
    this.confirmationService.confirm('Delete Product', 'Are you sure you want to delete?')
      .then((confirmed: boolean) => {
        if (confirmed) {
          let value: Product = event.data;
          this.productService.deleteProduct(value.id).subscribe((event.confirm.resolve()), event.confirm.reject());
        } else {
          event.confirm.reject();
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/product/list', event.data.id]);
  }

  fetchSettings() {
    return {
      hideSubHeader: true,
      actions: {
        position: 'right',
        delete: this.settingsDelete,
        custom: PRODUCT_CUSTOM,
      },
      add: PRODUCT_ADD,
      edit: PRODUCT_MODIFY,
      delete: PRODUCT_DELETE,
      columns: PRODUCT_HEADER
    }
  }

  private fetchData(filter: any = {}): void {
    this.productService.getProducts(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
          const response: GenericResponse<PageResponse<Product>> = p as GenericResponse<PageResponse<Product>>;
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

  onCustomAction(event) {
    switch ( event.action) {
      case PRODUCT_CUSTOM[0].name: //edit
        this.router.navigate(['pages/inventory/product/edit', event.data.id]);
        break;
    }
  }

}
