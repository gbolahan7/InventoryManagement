import { Component } from '@angular/core';
import {
  PRODUCT_REQUEST_ADD,
  PRODUCT_REQUEST_DELETE,
  PRODUCT_REQUEST_HEADER,
  PRODUCT_REQUEST_MODIFY,
  ProductRequest
} from "../product.data";
import {LocalDataSource} from "ng2-smart-table";
import {ProductService} from "../product.service";
import {Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-product-request-list',
  styleUrls: ['./product-request-list.component.scss'],
  templateUrl: './product-request-list.component.html',
})
export class ProductRequestListComponent {

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: PRODUCT_REQUEST_ADD,
    edit: PRODUCT_REQUEST_MODIFY,
    delete: PRODUCT_REQUEST_DELETE,
    columns: PRODUCT_REQUEST_HEADER
  };

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private productService: ProductService, private router:Router) {
    this.fetchData();
  }


  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/product/request', event.data.requestId]);
  }


  private fetchData(filter: any = {}): void {
    this.productService.getProductRequests(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<ProductRequest>> = p as GenericResponse<PageResponse<ProductRequest>>;
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
