import {Component, Input, OnInit} from '@angular/core';
import {
  ProductHistoryAudit,
} from "../product.data";
import {ProductService} from "../product.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-product-audit-revision',
  styleUrls: ['./product-audit-revision.component.scss'],
  templateUrl: './product-audit-revision.component.html',
})
export class ProductAuditRevisionComponent implements OnInit{

  productAudit: ProductHistoryAudit = null;
  dataPresent: boolean  = false;

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,) {

  }

  private fetchData(id: number, revisionId): void {
    this.productService.getProductAudit(id, revisionId)
      .subscribe(((p: object) => {
        const response: GenericResponse<ProductHistoryAudit> = p as GenericResponse<ProductHistoryAudit>;
        this.productAudit = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id, params.revisionId);
    });
  }


}
