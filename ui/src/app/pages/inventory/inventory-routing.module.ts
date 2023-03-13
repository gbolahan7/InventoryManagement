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

const routes: Routes = [{
  path: '',
  component: InventoryComponent,
  children: [
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
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InventoryRoutingModule {
}
