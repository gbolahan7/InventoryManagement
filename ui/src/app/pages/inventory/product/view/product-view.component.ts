import {Component, Inject, OnInit, Optional} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Product, ProductAudit, ProductRequest} from "../product.data";
import {BASE_PATH} from "../../../../app.module";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {BehaviorSubject, Observable, of} from "rxjs";
import {map} from "rxjs/operators";

@Component({
  selector: 'inventory-product-view',
  styleUrls: ['./product-view.component.scss'],
  templateUrl: './product-view.component.html',
})
export class ProductViewComponent implements OnInit{

  product: Product = null;
  dataPresent: boolean  = false;
  warehouseStyle: string = "display: none"
  warehouseChecked: boolean = false;
  safePictureUrl: SafeUrl;

  constructor(private productService: ProductService,
              private sanitizer: DomSanitizer,
              private activatedRoute: ActivatedRoute,) {
  }

  private fetchData(id: number): void {
    this.productService.getProduct(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<Product> = p as GenericResponse<Product>;
        this.product = response.data;
        if(this.product && !!this.product.warehouse && this.product.warehouse.trim().length > 0) {
          this.warehouseChecked = true;
          this.warehouseStyle = "display: block";
        }
        if(this.product.pictureUrl) this.getProductImage(this.product.pictureUrl);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
    });
  }

  getProductImage(url: string): void {
    this.productService
      .getProductAttachment(url)
      .pipe(
        map(p => {
          return this.sanitizer.bypassSecurityTrustUrl((window.URL.createObjectURL(p)));
        })
      ).subscribe(val => {
        this.safePictureUrl = val;
      })
  }
  get filePreview(): SafeUrl {
    return  this.safePictureUrl;
  }

}
