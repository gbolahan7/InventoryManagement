import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  NbAccordionModule,
  NbButtonModule,
  NbCardModule, NbInputModule,
  NbListModule,
  NbRouteTabsetModule, NbSelectModule,
  NbStepperModule,
  NbTabsetModule, NbTagModule, NbUserModule,
} from '@nebular/theme';

import { ThemeModule } from '../../theme/theme.module';
import { InventoryRoutingModule } from './inventory-routing.module';
import { InventoryComponent } from './inventory.component';
import { CategoryComponent } from './category/category.component';
import {CategoryListComponent} from "./category/list/category-list.component";
import {CategoryRequestListComponent} from "./category/request-list/category-request-list.component";
import {CategoryHomeComponent} from "./category/home/category-list.component";
import {Ng2SmartTableModule} from "ng2-smart-table";
import {CategoryService} from "./category/category.service";
import {CategoryViewComponent} from "./category/view/category-view.component";
import {CategoryCreateComponent} from "./category/create/category-create.component";
import {CategoryRequestViewComponent} from "./category/request-view/category-request-view.component";
import {CategoryEditComponent} from "./category/edit/category-edit.component";
import {CategoryAuditListComponent} from "./category/audit/category-audit.list.component";
import {CategoryAuditRevisionComponent} from "./category/audit-revision/category-audit-revision.component";
import {NbSecurityModule} from "@nebular/security";


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
    InventoryRoutingModule,
    Ng2SmartTableModule,
    NbInputModule,
    NbTagModule,
    NbSelectModule,
    NbSecurityModule,
  ],
  declarations: [
    InventoryComponent,
    CategoryComponent,
    CategoryListComponent,
    CategoryRequestListComponent,
    CategoryHomeComponent,
    CategoryViewComponent,
    CategoryCreateComponent,
    CategoryRequestViewComponent,
    CategoryEditComponent,
    CategoryAuditListComponent,
    CategoryAuditRevisionComponent
  ],
  providers: [
    CategoryService
  ],
})
export class InventoryModule { }
