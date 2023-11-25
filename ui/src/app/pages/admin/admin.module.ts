import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
    NbAccordionModule, NbButtonGroupModule,
    NbButtonModule,
    NbCardModule, NbCheckboxModule, NbInputModule,
    NbListModule,
    NbRouteTabsetModule, NbSelectModule,
    NbStepperModule,
    NbTabsetModule, NbTagModule, NbUserModule,
} from '@nebular/theme';

import { ThemeModule } from '../../theme/theme.module';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import {Ng2SmartTableModule} from "ng2-smart-table";
import {NbSecurityModule} from "@nebular/security";
import {RoleListComponent} from "./role/list/role-list.component";
import {RoleComponent} from "./role/role.component";
import {RoleService} from "./role/role.service";
import {UserListComponent} from "./user/list/user-list.component";
import {UserComponent} from "./user/user.component";
import {UserService} from "./user/user.service";


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
        AdminRoutingModule,
        Ng2SmartTableModule,
        NbInputModule,
        NbTagModule,
        NbSelectModule,
        NbSecurityModule,
        NbCheckboxModule,
        NbButtonGroupModule,
    ],
  declarations: [
    AdminComponent,
    RoleComponent,
    RoleListComponent,
    UserListComponent,
    UserComponent
  ],
  providers: [
    RoleService,
    UserService
  ],
})
export class AdminModule { }
