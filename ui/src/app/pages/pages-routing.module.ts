import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';
import {NoAccessComponent} from "./miscellaneous/no-access/no-access.component";
import {RoleGuard} from "./auth/service/role-guard.services";

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: '',
      component: NotFoundComponent,
      canActivate: [RoleGuard],
      data: [
        { permission: 'product', resource: 'add'},
      ],
    },
    {
      path: 'inventory',
      canActivate: [RoleGuard],
      data: [
        { permission: 'category', resource: 'create'},
        { permission: 'category', resource: 'view'},
        { permission: 'category', resource: 'access'},
        { permission: 'category', resource: 'modify'},
      ],
      loadChildren: () => import('./inventory/inventory.module')
        .then(m => m.InventoryModule),
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
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
