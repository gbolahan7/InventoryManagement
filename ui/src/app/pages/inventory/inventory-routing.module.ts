import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {InventoryComponent} from './inventory.component';
import {CategoryComponent} from "./category/category.component";
import {CategoryListComponent} from "./category/list/category-list.component";
import {CategoryRequestListComponent} from "./category/request-list/category-request-list.component";
import {CategoryHomeComponent} from "./category/home/category-list.component";
import {CategoryViewComponent} from "./category/view/category-view.component";
import {CategoryCreateComponent} from "./category/create/category-create.component";
import {CategoryRequestViewComponent} from "./category/request-view/category-request-view.component";
import {CategoryEditComponent} from "./category/edit/category-edit.component";
import {CategoryAuditListComponent} from "./category/audit/category-audit.list.component";
import {CategoryAuditRevisionComponent} from "./category/audit-revision/category-audit-revision.component";
import {RoleGuard} from "../auth/service/role-guard.services";
import {UnitComponent} from "./unit/unit.component";
import {UnitHomeComponent} from "./unit/home/unit-list.component";
import {UnitListComponent} from "./unit/list/unit-list.component";
import {UnitViewComponent} from "./unit/view/unit-view.component";
import {UnitEditComponent} from "./unit/edit/unit-edit.component";
import {UnitRequestListComponent} from "./unit/request-list/unit-request-list.component";
import {UnitRequestViewComponent} from "./unit/request-view/unit-request-view.component";
import {UnitCreateComponent} from "./unit/create/unit-create.component";
import {UnitAuditListComponent} from "./unit/audit/unit-audit.list.component";
import {UnitAuditRevisionComponent} from "./unit/audit-revision/unit-audit-revision.component";
import {ProductAuditRevisionComponent} from "./product/audit-revision/product-audit-revision.component";
import {ProductAuditListComponent} from "./product/audit/product-audit.list.component";
import {ProductCreateComponent} from "./product/create/product-create.component";
import {ProductRequestViewComponent} from "./product/request-view/product-request-view.component";
import {ProductRequestListComponent} from "./product/request-list/product-request-list.component";
import {ProductEditComponent} from "./product/edit/product-edit.component";
import {ProductViewComponent} from "./product/view/product-view.component";
import {ProductListComponent} from "./product/list/product-list.component";
import {ProductHomeComponent} from "./product/home/product-list.component";
import {ProductComponent} from "./product/product.component";
import {
  PurchaseOrderAuditRevisionComponent
} from "./purchase-order/audit-revision/purchase-order-audit-revision.component";
import {PurchaseOrderAuditListComponent} from "./purchase-order/audit/purchase-order-audit.list.component";
import {PurchaseOrderCreateComponent} from "./purchase-order/create/purchase-order-create.component";
import {PurchaseOrderRequestViewComponent} from "./purchase-order/request-view/purchase-order-request-view.component";
import {PurchaseOrderRequestListComponent} from "./purchase-order/request-list/purchase-order-request-list.component";
import {PurchaseOrderEditComponent} from "./purchase-order/edit/purchase-order-edit.component";
import {PurchaseOrderViewComponent} from "./purchase-order/view/purchase-order-view.component";
import {PurchaseOrderListComponent} from "./purchase-order/list/purchase-order-list.component";
import {PurchaseOrderHomeComponent} from "./purchase-order/home/purchase-order-list.component";
import {PurchaseOrderComponent} from "./purchase-order/purchase-order.component";

const routes: Routes = [{
  path: '',
  component: InventoryComponent,
  children: [
    {
      path: 'product',
      component: ProductComponent,
      children: [
        {
          path: '',
          redirectTo: 'home',
          pathMatch: 'full',
        },
        {
          path: 'home',
          component: ProductHomeComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'view'},
          ],
        },
        {
          path: 'list',
          component: ProductListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'view'},
          ],
        },
        {
          path: 'list/:id',
          component: ProductViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'view'},
          ],
        },
        {
          path: 'edit/:id',
          component: ProductEditComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'modify'},
          ],
        },
        {
          path: 'request',
          component: ProductRequestListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'view'},
          ],
        },
        {
          path: 'request/:id',
          component: ProductRequestViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'view'},
          ],
        },
        {
          path: 'create',
          component: ProductCreateComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'create'},
          ],
        },
        {
          path: 'audit/:id',
          component: ProductAuditListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'view'},
          ],
        },
        {
          path: 'audit/:id/revision/:revisionId',
          component: ProductAuditRevisionComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'product', resource: 'view'},
          ],
        },
      ],
    },
    {
      path: 'category',
      component: CategoryComponent,
      children: [
        {
          path: '',
          redirectTo: 'home',
          pathMatch: 'full',
        },
        {
          path: 'home',
          component: CategoryHomeComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'view'},
          ],
        },
        {
          path: 'list',
          component: CategoryListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'view'},
          ],
        },
        {
          path: 'list/:id',
          component: CategoryViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'view'},
          ],
        },
        {
          path: 'edit/:id',
          component: CategoryEditComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'modify'},
          ],
        },
        {
          path: 'request',
          component: CategoryRequestListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'view'},
          ],
        },
        {
          path: 'request/:id',
          component: CategoryRequestViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'view'},
          ],
        },
        {
          path: 'create',
          component: CategoryCreateComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'create'},
          ],
        },
        {
          path: 'audit/:id',
          component: CategoryAuditListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'view'},
          ],
        },
        {
          path: 'audit/:id/revision/:revisionId',
          component: CategoryAuditRevisionComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'category', resource: 'view'},
          ],
        },
      ],
    },
    {
      path: 'unit',
      component: UnitComponent,
      children: [
        {
          path: '',
          redirectTo: 'home',
          pathMatch: 'full',
        },
        {
          path: 'home',
          component: UnitHomeComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'view'},
          ],
        },
        {
          path: 'list',
          component: UnitListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'view'},
          ],
        },
        {
          path: 'list/:id',
          component: UnitViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'view'},
          ],
        },
        {
          path: 'edit/:id',
          component: UnitEditComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'modify'},
          ],
        },
        {
          path: 'request',
          component: UnitRequestListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'view'},
          ],
        },
        {
          path: 'request/:id',
          component: UnitRequestViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'view'},
          ],
        },
        {
          path: 'create',
          component: UnitCreateComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'create'},
          ],
        },
        {
          path: 'audit/:id',
          component: UnitAuditListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'view'},
          ],
        },
        {
          path: 'audit/:id/revision/:revisionId',
          component: UnitAuditRevisionComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'unit', resource: 'view'},
          ],
        },
      ],
    },
    {
      path: 'purchase-order',
      component: PurchaseOrderComponent,
      children: [
        {
          path: '',
          redirectTo: 'home',
          pathMatch: 'full',
        },
        {
          path: 'home',
          component: PurchaseOrderHomeComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'view'},
          ],
        },
        {
          path: 'list',
          component: PurchaseOrderListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'view'},
          ],
        },
        {
          path: 'list/:id',
          component: PurchaseOrderViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'view'},
          ],
        },
        {
          path: 'edit/:id',
          component: PurchaseOrderEditComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'modify'},
          ],
        },
        {
          path: 'request',
          component: PurchaseOrderRequestListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'view'},
          ],
        },
        {
          path: 'request/:id',
          component: PurchaseOrderRequestViewComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'view'},
          ],
        },
        {
          path: 'create',
          component: PurchaseOrderCreateComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'create'},
          ],
        },
        {
          path: 'audit/:id',
          component: PurchaseOrderAuditListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'view'},
          ],
        },
        {
          path: 'audit/:id/revision/:revisionId',
          component: PurchaseOrderAuditRevisionComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'purchase_order', resource: 'view'},
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
export class InventoryRoutingModule {
}
