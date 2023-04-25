import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  NbAccordionModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbDatepickerModule,
  NbInputModule,
  NbListModule,
  NbRouteTabsetModule,
  NbSelectModule,
  NbStepperModule,
  NbTabsetModule,
  NbTagModule,
  NbUserModule,
} from '@nebular/theme';

import {ThemeModule} from '../../theme/theme.module';
import {ReportRoutingModule} from './report-routing.module';
import {ReportComponent} from './report.component';
import {Ng2SmartTableModule} from "ng2-smart-table";
import {NbSecurityModule} from "@nebular/security";
import {ReportUnitComponent} from "./unit/report-unit.component";
import {ReportService} from "./report.service";
import {CategoryService} from "../inventory/category/category.service";
import {UnitService} from "../inventory/unit/unit.service";
import {PurchaseOrderService} from "../inventory/purchase-order/purchase-order.service";
import {ReportProductComponent} from "./product/report-product.component";
import {ProductService} from "../inventory/product/product.service";
import {ReportPurchaseOrderComponent} from "./purchase-order/report-purchase-order.component";
import {ReportCategoryComponent} from "./category/report-category.component";
import {ReportPurchaseOrderItemComponent} from "./purchase-order-items/report-purchase-order-item.component";


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
    ReportRoutingModule,
    Ng2SmartTableModule,
    NbInputModule,
    NbTagModule,
    NbSelectModule,
    NbSecurityModule,
    NbDatepickerModule,
    NbCheckboxModule,
  ],
  declarations: [
    ReportComponent,
    ReportProductComponent,
    ReportPurchaseOrderComponent,
    ReportCategoryComponent,
    ReportPurchaseOrderItemComponent,
    ReportUnitComponent
  ],
  providers: [
    ReportService,
    CategoryService,
    UnitService,
    ProductService,
    PurchaseOrderService,
  ],
})
export class ReportModule {
}
