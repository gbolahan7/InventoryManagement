import { Component } from '@angular/core';

@Component({
  selector: 'inventory-product-home',
  styles: [`
    nb-tabset {
      height: 100%;
      display: flex;
      flex-direction: column;
    }

    :host ::ng-deep ngx-tab1, :host ::ng-deep ngx-tab2 {
      display: block;
      padding: 1rem 2rem;
    }
  `],
  template: `
    <div class="row" *nbIsGranted="['product', 'view']">
      <div class="col-12">

        <nb-card>
          <nb-tabset fullWidth>
            <nb-tab [tabTitle]= tab[0]>
              <inventory-product-list></inventory-product-list>
            </nb-tab>
            <nb-tab [tabTitle]= tab[1]>
              <inventory-product-request-list></inventory-product-request-list>
            </nb-tab>
          </nb-tabset>
        </nb-card>

      </div>
    </div>
  `,
})
export class ProductHomeComponent {

  tab = ['Product List', 'Product Request']
}
