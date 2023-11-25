import {Component, Input, OnInit} from '@angular/core';
import {GenericResponse} from "../template/http-util";
import {Product} from "../../../pages/inventory/product/product.data";
import {PurchaseOrderService} from "../../../pages/inventory/purchase-order/purchase-order.service";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Component({
 selector: 'custom-input-image',
 template: `
     <qrcode
       [qrdata]="value | async"
       [allowEmptyString]="true"
       [cssClass]="'center'"
       [colorDark]="'#000000ff'"
       [colorLight]="'#ffffffff'"
       [elementType]="'canvas'"
       [errorCorrectionLevel]="'M'"
       [margin]="4"
       [scale]="1"
       [title]="title | async"
       [width]="150"
     ></qrcode>
`,
})
export class CustomInputImageComponent implements OnInit {
  value: Observable<string>;
  title: Observable<string>
  @Input() rowData: any;

  constructor(private purchaseOrderService: PurchaseOrderService,) {
  }
  ngOnInit(): void {
    this.fetchProductData(this.rowData.productCode, this.rowData.quantity);
}

  private fetchProductData(productCode: string, chosenQuantity: number): void {
    let productObserve: Observable<Product> = this.purchaseOrderService.getProducts().pipe(
      map((p: object) => {
        const response: GenericResponse<Product[]> = p as GenericResponse<Product[]>;
        return response.data.find(code => code.code === productCode);
      })
    );
    this.value = productObserve.pipe(
      map((product: Product) => {
        return `
          Name: ${product.name}
          Code: ${product.code}
          Total Quantity: ${product.quantity}
          Quantity Left: ${product.quantity - chosenQuantity}
          Price: ${product.unitPrice}
          Unit: ${product.unit}
          Category: ${product.category}
          `;
      }));
    this.title = productObserve.pipe(
      map((product: Product) => `${product.name} - ${product.code}`)
    );
  }
}
