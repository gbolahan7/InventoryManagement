import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  NbAccordionModule,
  NbButtonModule,
  NbCardModule, NbCheckboxModule, NbDatepickerModule, NbInputModule,
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
import {UnitComponent} from "./unit/unit.component";
import {UnitListComponent} from "./unit/list/unit-list.component";
import {UnitRequestListComponent} from "./unit/request-list/unit-request-list.component";
import {UnitHomeComponent} from "./unit/home/unit-list.component";
import {UnitViewComponent} from "./unit/view/unit-view.component";
import {UnitCreateComponent} from "./unit/create/unit-create.component";
import {UnitRequestViewComponent} from "./unit/request-view/unit-request-view.component";
import {UnitEditComponent} from "./unit/edit/unit-edit.component";
import {UnitAuditListComponent} from "./unit/audit/unit-audit.list.component";
import {UnitAuditRevisionComponent} from "./unit/audit-revision/unit-audit-revision.component";
import {UnitService} from "./unit/unit.service";
import {ProductService} from "./product/product.service";
import {ProductAuditRevisionComponent} from "./product/audit-revision/product-audit-revision.component";
import {ProductAuditListComponent} from "./product/audit/product-audit.list.component";
import {ProductEditComponent} from "./product/edit/product-edit.component";
import {ProductRequestViewComponent} from "./product/request-view/product-request-view.component";
import {ProductCreateComponent} from "./product/create/product-create.component";
import {ProductViewComponent} from "./product/view/product-view.component";
import {ProductHomeComponent} from "./product/home/product-list.component";
import {ProductRequestListComponent} from "./product/request-list/product-request-list.component";
import {ProductListComponent} from "./product/list/product-list.component";
import {ProductComponent} from "./product/product.component";
import {PurchaseOrderService} from "./purchase-order/purchase-order.service";
import {
  PurchaseOrderAuditRevisionComponent
} from "./purchase-order/audit-revision/purchase-order-audit-revision.component";
import {PurchaseOrderAuditListComponent} from "./purchase-order/audit/purchase-order-audit.list.component";
import {PurchaseOrderEditComponent} from "./purchase-order/edit/purchase-order-edit.component";
import {PurchaseOrderRequestViewComponent} from "./purchase-order/request-view/purchase-order-request-view.component";
import {PurchaseOrderCreateComponent} from "./purchase-order/create/purchase-order-create.component";
import {PurchaseOrderViewComponent} from "./purchase-order/view/purchase-order-view.component";
import {PurchaseOrderHomeComponent} from "./purchase-order/home/purchase-order-list.component";
import {PurchaseOrderRequestListComponent} from "./purchase-order/request-list/purchase-order-request-list.component";
import {PurchaseOrderListComponent} from "./purchase-order/list/purchase-order-list.component";
import {PurchaseOrderComponent} from "./purchase-order/purchase-order.component";


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
    NbDatepickerModule,
    NbCheckboxModule,
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
    CategoryAuditRevisionComponent,
    UnitComponent,
    UnitListComponent,
    UnitRequestListComponent,
    UnitHomeComponent,
    UnitViewComponent,
    UnitCreateComponent,
    UnitRequestViewComponent,
    UnitEditComponent,
    UnitAuditListComponent,
    UnitAuditRevisionComponent,
    ProductComponent,
    ProductListComponent,
    ProductRequestListComponent,
    ProductHomeComponent,
    ProductViewComponent,
    ProductCreateComponent,
    ProductRequestViewComponent,
    ProductEditComponent,
    ProductAuditListComponent,
    ProductAuditRevisionComponent,
    PurchaseOrderComponent,
    PurchaseOrderListComponent,
    PurchaseOrderRequestListComponent,
    PurchaseOrderHomeComponent,
    PurchaseOrderViewComponent,
    PurchaseOrderCreateComponent,
    PurchaseOrderRequestViewComponent,
    PurchaseOrderEditComponent,
    PurchaseOrderAuditListComponent,
    PurchaseOrderAuditRevisionComponent,
  ],
  providers: [
    CategoryService,
    UnitService,
    PurchaseOrderService,
    ProductService,
  ],
})
export class InventoryModule { }
