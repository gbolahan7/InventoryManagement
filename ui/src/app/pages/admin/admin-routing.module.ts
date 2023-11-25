import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AdminComponent} from './admin.component';
import {RoleGuard} from "../auth/service/role-guard.services";
import {RoleComponent} from "./role/role.component";
import {RoleListComponent} from "./role/list/role-list.component";
import {UserComponent} from "./user/user.component";
import {UserListComponent} from "./user/list/user-list.component";

const routes: Routes = [{
  path: '',
  component: AdminComponent,
  children: [
    {
      path: 'role',
      component: RoleComponent,
      children: [
        {
          path: '',
          redirectTo: 'list',
          pathMatch: 'full',
        },
        {
          path: 'list',
          component: RoleListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'role', resource: 'operation'},
          ],
        },
      ],
    },
    {
      path: 'user',
      component: UserComponent,
      children: [
        {
          path: '',
          redirectTo: 'list',
          pathMatch: 'full',
        },
        {
          path: 'list',
          component: UserListComponent,
          canActivate: [RoleGuard],
          data: [
            { permission: 'user', resource: 'operation'},
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
export class AdminRoutingModule {
}
