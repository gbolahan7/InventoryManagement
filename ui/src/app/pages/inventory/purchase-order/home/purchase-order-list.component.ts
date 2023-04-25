import { Component } from '@angular/core';

@Component({
  selector: 'inventory-purchase-order-home',
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
    <div class="row" *nbIsGranted="['purchase_order', 'view']">
      <div class="col-12">

        <nb-card>
          <nb-tabset fullWidth>
            <nb-tab [tabTitle]= tab[0]>
              <inventory-purchase-order-list></inventory-purchase-order-list>
            </nb-tab>
            <nb-tab [tabTitle]= tab[1]>
              <inventory-purchase-order-request-list></inventory-purchase-order-request-list>
            </nb-tab>
          </nb-tabset>
        </nb-card>

      </div>
    </div>
  `,
})
export class PurchaseOrderHomeComponent {

  tab = ['Purchase Order List', 'Purchase Order Request']
}
