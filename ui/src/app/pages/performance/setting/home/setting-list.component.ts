import { Component } from '@angular/core';

@Component({
  selector: 'performance-setting-home',
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
    <div class="row" *nbIsGranted="['setting', 'view']">
      <div class="col-12">

        <nb-card>
          <nb-tabset fullWidth>
            <nb-tab [tabTitle]= tab[0]>
              <performance-setting-view [_id]="1"></performance-setting-view>
            </nb-tab>
            <nb-tab [tabTitle]= tab[1]>
              <performance-setting-request-list></performance-setting-request-list>
            </nb-tab>
          </nb-tabset>
        </nb-card>

      </div>
    </div>
  `,
})
export class SettingHomeComponent {

  tab = ['Performance Setting', 'Setting Request']
}
