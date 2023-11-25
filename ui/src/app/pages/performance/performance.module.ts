import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  NbAccordionModule,
  NbButtonModule,
  NbCardModule, NbCheckboxModule, NbDatepickerModule, NbInputModule,
  NbListModule,
  NbRouteTabsetModule, NbSelectModule,
  NbStepperModule,
  NbTabsetModule, NbTagModule, NbTimepickerModule, NbUserModule,
} from '@nebular/theme';

import { ThemeModule } from '../../theme/theme.module';
import { PerformanceRoutingModule } from './performance-routing.module';
import { PerformanceComponent } from './performance.component';
import {Ng2SmartTableModule} from "ng2-smart-table";
import {NbSecurityModule} from "@nebular/security";
import {SettingService} from "./setting/setting.service";
import {SettingComponent} from "./setting/setting.component";
import {SettingRequestListComponent} from "./setting/request-list/setting-request-list.component";
import {SettingHomeComponent} from "./setting/home/setting-list.component";
import {SettingViewComponent} from "./setting/view/setting-view.component";
import {SettingRequestViewComponent} from "./setting/request-view/setting-request-view.component";
import {SettingEditComponent} from "./setting/edit/setting-edit.component";
import {SettingAuditListComponent} from "./setting/audit/setting-audit.list.component";
import {SettingAuditRevisionComponent} from "./setting/audit-revision/setting-audit-revision.component";
import {PerformanceStaffComponent} from "./staff/performance-staff.component";

@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    ThemeModule,
    NbTabsetModule,
    NbRouteTabsetModule,
    NbStepperModule,
    NbCardModule,
    NbButtonModule,
    NbListModule,
    NbAccordionModule,
    NbUserModule,
    PerformanceRoutingModule,
    NbDatepickerModule.forRoot(),
    NbTimepickerModule.forRoot(),
    Ng2SmartTableModule,
    NbInputModule,
    NbTagModule,
    NbSelectModule,
    NbSecurityModule,
    NbDatepickerModule,
    NbCheckboxModule,
  ],
  declarations: [
    PerformanceComponent,
    SettingComponent,
    SettingRequestListComponent,
    SettingHomeComponent,
    SettingViewComponent,
    SettingRequestViewComponent,
    SettingEditComponent,
    SettingAuditListComponent,
    SettingAuditRevisionComponent,
    PerformanceStaffComponent
  ],
  providers: [
    SettingService
  ],
})
export class PerformanceModule { }
