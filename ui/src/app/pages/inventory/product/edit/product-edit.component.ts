import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {PictureSnippet, Product, ProductAudit, ProductRequest} from "../product.data";
import {NbTagComponent} from "@nebular/theme";
import {Observable, of} from "rxjs";
import {Category} from "../../category/category.data";
import {Unit} from "../../unit/unit.data";
import {CategoryService} from "../../category/category.service";
import {map} from "rxjs/operators";
import {UnitService} from "../../unit/unit.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'inventory-product-edit',
  styleUrls: ['./product-edit.component.scss'],
  templateUrl: './product-edit.component.html',
})
export class ProductEditComponent  implements OnInit{

  product: ProductRequest = new ProductRequest();
  dataPresent: boolean  = false;
  statuses = [{name: 'Active', key: 'Active'},{name: 'Inactive', key: 'Inactive'}];
  categories: Observable<Category[]> = of([]);
  units: Observable<Unit[]> = of([]);
  warehouseStyle: string = "display: none"
  warehouseChecked: boolean = false;
  file: File;
  originalProfilePic = false;
  selectedFile: PictureSnippet;
  private reader = new FileReader();
  class = 'hide-text';

  constructor(private sanitizer: DomSanitizer, private productService: ProductService,
              private router:Router, private activatedRoute: ActivatedRoute,
              private categoryService: CategoryService, private unitService: UnitService) {

  }

  private fetchData(id: number): void {
    this.productService.getProduct(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<Product> = p as GenericResponse<Product>;
        this.product = this.mapFromProductToProductRequest(response.data);
        if(this.product && !!this.product.warehouse && this.product.warehouse.trim().length > 0) {
          this.warehouseChecked = true;
          this.warehouseStyle = "display: block";
        }
        if(this.product.pictureUrl) this.getProductImage(this.product.pictureUrl);
        else this.originalProfilePic = true;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  mapFromProductToProductRequest(product: Product): ProductRequest {
    let productRequest: ProductRequest = JSON.parse(JSON.stringify(product))
    productRequest.productId = product.id;
    return productRequest;
  }

  edit(): void {
    if(this.validatedMandatoryFields(this.product)) {
      if(!!this.selectedFile && !!this.selectedFile.src)
        this.product.picturePayload = this.selectedFile.src;
      this.editProduct(this.product);
    }

  }

  validatedMandatoryFields(product: ProductRequest): boolean {
    return product && !!product.name && !!product.quantity && !!product.status &&
      !!product.code && !!product.category && !!product.unitPrice && !!product.unit
  }

  private editProduct(payload: any = {}): void {
    this.productService.editProductRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/inventory/product/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  loadCategories(categoryService: CategoryService, filter: any) {
    this.categories = categoryService.getCategories(DEFAULT_PAGE_REQUEST, filter)
      .pipe(
        map((p: object) => {
          const response: GenericResponse<PageResponse<Category>> = p as GenericResponse<PageResponse<Category>>;
          return response.data.content;
        })
      )
  }

  loadUnits(unitService: UnitService, filter: any) {
    this.units = unitService.getUnits(DEFAULT_PAGE_REQUEST, filter)
      .pipe(
        map((p: object) => {
          const response: GenericResponse<PageResponse<Unit>> = p as GenericResponse<PageResponse<Unit>>;
          return response.data.content;
        })
      )
  }

  onCheckedWarehouse(checked: any) {
    if(checked) this.warehouseStyle = "display: block";
    else this.warehouseStyle = "display: none";
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
    return this.sanitizer.bypassSecurityTrustUrl((window.URL.createObjectURL(this.file)));
  }

  getProductImage(url: string): void {
    this.productService.getProductAttachment(url).subscribe(val => {
      this.file = val;
    })
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
      const filter: any = {status: 'Active'};
      this.loadCategories(this.categoryService, filter);
      this.loadUnits(this.unitService, filter);
    });
  }
}
