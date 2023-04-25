import { Component } from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Product, ProductAudit, ProductRequest} from "../product.data";

@Component({
  selector: 'inventory-product-request-view',
  styleUrls: ['./product-request-view.component.scss'],
  templateUrl: './product-request-view.component.html',
})
export class ProductRequestViewComponent {

  productRequest: ProductRequest = null;
  dataPresent: boolean  = false;

  APPROVE: string = 'Approve';
  REJECT: string = 'Reject'

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchDataRequest(params.id)
    });
  }

  private fetchDataRequest(id: number): void {
    this.productService.getProductRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<ProductRequest> = p as GenericResponse<ProductRequest>;
        this.productRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  onPermission(event, operation: string): void {
    if(operation === this.APPROVE) {
      this.approveDataRequest(this.productRequest.requestId);
    }else if(operation == this.REJECT) {
      this.rejectDataRequest(this.productRequest.requestId);
    }
  }

  private approveDataRequest(id: number): void {
    this.productService.approveProductRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<ProductRequest> = p as GenericResponse<ProductRequest>;
        this.productRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  private rejectDataRequest(id: number): void {
    this.productService.rejectProductRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<ProductRequest> = p as GenericResponse<ProductRequest>;
        this.productRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
