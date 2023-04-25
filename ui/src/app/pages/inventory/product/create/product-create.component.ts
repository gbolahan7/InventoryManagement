import { Component } from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {PictureSnippet, Product, ProductAudit, ProductRequest} from "../product.data";
import {CategoryService} from "../../category/category.service";
import {UnitService} from "../../unit/unit.service";
import {map} from "rxjs/operators";
import {Category} from "../../category/category.data";
import {Observable, observable, of} from "rxjs";
import {Unit} from "../../unit/unit.data";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'inventory-product-create',
  styleUrls: ['./product-create.component.scss'],
  templateUrl: './product-create.component.html',
})
export class ProductCreateComponent {

  product: Product = new Product();
  dataPresent: boolean  = false;
  statuses = [{name: 'Active', key: 'Active'},{name: 'Inactive', key: 'Inactive'}];
  categories: Observable<Category[]> = of([]);
  units: Observable<Unit[]> = of([]);
  warehouseStyle: string = "display: none";
  file: File;
  originalProfilePic = true;
  selectedFile: PictureSnippet;
  private reader = new FileReader();
  class = 'hide-text';

  constructor(private sanitizer: DomSanitizer, private productService: ProductService, private router:Router,
              private categoryService: CategoryService, private unitService: UnitService) {
    this.selectedFile = new PictureSnippet(null, null);
    this.product.status = this.statuses[0].name;
    const filter: any = {status: 'Active'};
    this.loadCategories(categoryService, filter);
    this.loadUnits(unitService, filter);
  }

  loadCategories(categoryService: CategoryService, filter: any) {
    this.categories = categoryService.getCategories(DEFAULT_PAGE_REQUEST, filter)
      .pipe(
        map((p: object) => {
          const response: GenericResponse<PageResponse<Category>> = p as GenericResponse<PageResponse<Category>>;
          const categories: Category[] =  response.data.content;
          if(categories && categories.length > 0) this.product.category = categories[0].name
          return categories;
        })
      )
  }

  loadUnits(unitService: UnitService, filter: any) {
    this.units = unitService.getUnits(DEFAULT_PAGE_REQUEST, filter)
      .pipe(
        map((p: object) => {
          const response: GenericResponse<PageResponse<Unit>> = p as GenericResponse<PageResponse<Unit>>;
          const units: Unit[] =  response.data.content;
          if(units && units.length > 0) this.product.unit = units[0].name
          return units;
        })
      )
  }

  create(): void {
    if(this.validatedMandatoryFields(this.product)) {
      if(!!this.selectedFile)
        this.product.picturePayload = this.selectedFile.src;
      this.createProduct(this.product);
    }
  }

  validatedMandatoryFields(product: Product): boolean {
    return product && !!product.name && !!product.quantity && !!product.status &&
      !!product.code && !!product.category && !!product.unitPrice && !!product.unit
  }

  private createProduct(payload: any = {}): void {
    this.productService.createProductRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/inventory/product/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  onCheckedWarehouse(checked: any) {
    if(checked) this.warehouseStyle = "display: block";
    else {
      this.warehouseStyle = "display: none";
      if(this.product){
        this.product.warehousePrice = null;
        this.product.warehouse = null;
      }
    }
  }

  processProfilePicture(e: any) {
    const file: File = e.target.files[0];
    this.reader.addEventListener('load', (event: any) => {
      this.selectedFile = new PictureSnippet(event.target.result, file);
      this.selectedFile.pending = true;
      this.onPictureSuccess();
      this.originalProfilePic = false;
      this.file = file;
    });
    this.reader.readAsDataURL(file);
  }

  private onPictureSuccess() {
    this.selectedFile.pending = false;
    this.selectedFile.status = 'ok';
  }

  get filePreview(): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl((
      window.URL.createObjectURL(this.file)));
  }

}
