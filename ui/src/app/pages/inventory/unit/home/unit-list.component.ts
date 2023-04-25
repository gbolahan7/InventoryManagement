import { Component } from '@angular/core';

@Component({
  selector: 'inventory-unit-home',
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
    <div class="row" *nbIsGranted="['unit', 'view']">
      <div class="col-12">

        <nb-card>
          <nb-tabset fullWidth>
            <nb-tab [tabTitle]= tab[0]>
              <inventory-unit-list></inventory-unit-list>
            </nb-tab>
            <nb-tab [tabTitle]= tab[1]>
              <inventory-unit-request-list></inventory-unit-request-list>
            </nb-tab>
          </nb-tabset>
        </nb-card>

      </div>
    </div>
  `,
})
export class UnitHomeComponent {

  tab = ['Unit List', 'Unit Request']
}
