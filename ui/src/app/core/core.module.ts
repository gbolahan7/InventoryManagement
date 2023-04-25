import {ModuleWithProviders, NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NbRoleProvider, NbSecurityModule} from '@nebular/security';
import {of as observableOf} from 'rxjs';
import {throwIfAlreadyLoaded} from './module-import-guard';
import {AnalyticsService, LayoutService, StateService,} from './utils';
import {ROLE_ACCESS_CONTROL} from "./utils/template/role-values";
import {SimpleRoleProvider} from "./utils/role.service";
import {ErrorService} from "./utils/error.service";
import {CustomInputEditorComponent} from "./utils/component/custom-input-editor.component";
import {FormsModule} from "@angular/forms";
import {NbButtonModule, NbCardModule, NbInputModule} from "@nebular/theme";
import {CustomInputImageComponent} from "./utils/component/custom-input-image.component";
import {QRCodeModule} from "angularx-qrcode";
import {ConfirmationDialogComponent} from "./utils/component/confirm-dialog/confirm-dialog.component";
import {ConfirmationDialogService} from "./utils/confirm-dialog.service";

export const CORE_PROVIDERS = [
  NbSecurityModule.forRoot({ accessControl: ROLE_ACCESS_CONTROL }).providers,
  {provide: NbRoleProvider, useClass: SimpleRoleProvider },
  AnalyticsService,
  LayoutService,
  StateService,
  ErrorService,
  ConfirmationDialogService
];

@NgModule({
  imports: [
    CommonModule,
    NbInputModule,
    FormsModule,
    QRCodeModule,
    NbCardModule,
    NbButtonModule,
  ],
  declarations: [
    CustomInputEditorComponent,
    ConfirmationDialogComponent,
    CustomInputImageComponent
  ],
  entryComponents: [ ConfirmationDialogComponent ],
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
