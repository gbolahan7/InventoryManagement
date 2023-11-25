import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {PerformanceComponent} from './performance.component';
import {RoleGuard} from "../auth/service/role-guard.services";
import {SettingComponent} from "./setting/setting.component";
import {SettingHomeComponent} from "./setting/home/setting-list.component";
import {SettingViewComponent} from "./setting/view/setting-view.component";
import {SettingEditComponent} from "./setting/edit/setting-edit.component";
import {SettingRequestListComponent} from "./setting/request-list/setting-request-list.component";
import {SettingRequestViewComponent} from "./setting/request-view/setting-request-view.component";
import {SettingAuditListComponent} from "./setting/audit/setting-audit.list.component";
import {SettingAuditRevisionComponent} from "./setting/audit-revision/setting-audit-revision.component";
import {PerformanceStaffComponent} from "./staff/performance-staff.component";

const routes: Routes = [{
  path: '',
  component: PerformanceComponent,
  children: [
    {
      path: 'staff',
      component: PerformanceStaffComponent,
      canActivate: [RoleGuard],
      data: [
        {permission: 'staff', resource: 'view'},
      ],
    },
    {
      path: 'setting',
      component: SettingComponent,
      children: [
        {
          path: '',
          redirectTo: 'home',
          pathMatch: 'full',
        },
        {
          path: 'home',
          component: SettingHomeComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'setting', resource: 'view'},
          ],
        },
        {
          path: 'list/:id',
          component: SettingViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'setting', resource: 'view'},
          ],
        },
        {
          path: 'edit/:id',
          component: SettingEditComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'setting', resource: 'modify'},
          ],
        },
        {
          path: 'request',
          component: SettingRequestListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'setting', resource: 'view'},
          ],
        },
        {
          path: 'request/:id',
          component: SettingRequestViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'setting', resource: 'view'},
          ],
        },
        {
          path: 'audit/:id',
          component: SettingAuditListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'setting', resource: 'view'},
          ],
        },
        {
          path: 'audit/:id/revision/:revisionId',
          component: SettingAuditRevisionComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'setting', resource: 'view'},
          ],
        },
      ],
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PerformanceRoutingModule {
}
