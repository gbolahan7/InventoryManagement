import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';
import {NoAccessComponent} from "./miscellaneous/no-access/no-access.component";
import {RoleGuard} from "./auth/service/role-guard.services";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {ReportModule} from "./report/report.module";
import {PerformanceModule} from "./performance/performance.module";

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      canActivate: [RoleGuard],
      component: DashboardComponent,
      data: [
        { permission: 'chart', resource: 'view'},
      ],
    },
    {
      path: 'inventory',
      canActivate: [RoleGuard],
      data: [
        { permission: 'purchase_order', resource: 'view'},
        { permission: 'category', resource: 'view'},
        { permission: 'unit', resource: 'view'},
        { permission: 'product', resource: 'view'},
      ],
      loadChildren: () => import('./inventory/inventory.module')
        .then(m => m.InventoryModule),
    },
    {
      path: 'report',
      canActivate: [RoleGuard],
      data: [
        { permission: 'report', resource: 'view'},
      ],
      loadChildren: () => import('./report/report.module')
        .then(m => m.ReportModule),
    },
    {
      path: 'performance',
      canActivate: [RoleGuard],
      data: [
        { permission: 'setting', resource: 'view'},
        { permission: 'staff', resource: 'view'},
      ],
      loadChildren: () => import('./performance/performance.module')
        .then(m => m.PerformanceModule),
    },
    {
      path: 'admin',
      canActivate: [RoleGuard],
      data: [
        { permission: 'role', resource: 'operation'},
      ],
      loadChildren: () => import('./admin/admin.module')
        .then(m => m.AdminModule),
    },
    {
      path: 'not-found',
      component: NotFoundComponent,
    },
    {
      path: 'no-access',
      component: NoAccessComponent,
      data: []
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
    {
      path: '**',
      component: NotFoundComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
