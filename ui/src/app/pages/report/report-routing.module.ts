import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {ReportComponent} from './report.component';
import {RoleGuard} from "../auth/service/role-guard.services";
import {ReportUnitComponent} from "./unit/report-unit.component";
import {ReportProductComponent} from "./product/report-product.component";
import {ReportPurchaseOrderComponent} from "./purchase-order/report-purchase-order.component";
import {ReportCategoryComponent} from "./category/report-category.component";
import {ReportPurchaseOrderItemComponent} from "./purchase-order-items/report-purchase-order-item.component";
import {ReportStaffPerformanceComponent} from "./staff-performance/report-staff-performance.component";

const routes: Routes = [{
  path: '',
  component: ReportComponent,
  children: [
    {
      path: 'product',
      component: ReportProductComponent,
      canActivate: [RoleGuard],
      data: [
        {permission: 'report', resource: 'view'},
      ],
    },
    {
      path: 'purchase-order',
      component: ReportPurchaseOrderComponent,
      canActivate: [RoleGuard],
      data: [
        {permission: 'report', resource: 'view'},
      ],
    },
    {
      path: 'purchase-order-item',
      component: ReportPurchaseOrderItemComponent,
      canActivate: [RoleGuard],
      data: [
        {permission: 'report', resource: 'view'},
      ],
    },
    {
      path: 'category',
      component: ReportCategoryComponent,
      canActivate: [RoleGuard],
      data: [
        {permission: 'report', resource: 'view'},
      ],
    },
    {
      path: 'unit',
      component: ReportUnitComponent,
      canActivate: [RoleGuard],
      data: [
        {permission: 'report', resource: 'view'},
      ],
    },
     {
        path: 'staff-performance',
        component: ReportStaffPerformanceComponent,
        canActivate: [RoleGuard],
        data: [
          {permission: 'report', resource: 'view'},
        ],
      },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ReportRoutingModule {
}
