import {ModuleWithProviders, NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NbRoleProvider, NbSecurityModule} from '@nebular/security';
import {of as observableOf} from 'rxjs';
import {throwIfAlreadyLoaded} from './module-import-guard';
import {AnalyticsService, LayoutService, StateService,} from './utils';
import {ROLE_ACCESS_CONTROL} from "./utils/template/role-values";
import {SimpleRoleProvider} from "./utils/role.service";
import {ErrorService} from "./utils/error.service";

export const CORE_PROVIDERS = [
  NbSecurityModule.forRoot({ accessControl: ROLE_ACCESS_CONTROL }).providers,
  {provide: NbRoleProvider, useClass: SimpleRoleProvider },
  AnalyticsService,
  LayoutService,
  StateService,
  ErrorService
];

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [],
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }

  static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: [
        ...CORE_PROVIDERS,
      ],
    };
  }
}
