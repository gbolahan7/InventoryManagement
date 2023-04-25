import {Component} from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../core/utils/template/http-util";
import {ReportService} from "../report.service";
import {Observable} from "rxjs";
import {ProductService} from "../../inventory/product/product.service";
import {Product, PRODUCT_HEADER} from "../../inventory/product/product.data";
import {PurchaseOrder, PurchaseOrder_HEADER} from "../../inventory/purchase-order/purchase-order.data";
import {PurchaseOrderService} from "../../inventory/purchase-order/purchase-order.service";

@Component({
  selector: 'report-purchase-order',
  styleUrls: ['./report-purchase-order.component.scss'],
  templateUrl: './report-purchase-order.component.html',
})
export class ReportPurchaseOrderComponent {

  formats = [{name: 'Pdf', value: 'pdf'}, {name: 'Excel', value: 'xlsx'}];
  format = 'pdf';
  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: false,
    edit: '',
    delete: '',
    columns: PurchaseOrder_HEADER
  };

  filterEntity: any = {};

  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private purchaseOrderService: PurchaseOrderService, private reportService: ReportService) {
    this.fetchData({}, true);
  }

  private fetchData(filter: any = {}, startup: boolean = false): void {
    this.purchaseOrderService.getPurchaseOrders(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<PurchaseOrder>> = p as GenericResponse<PageResponse<PurchaseOrder>>;
        this.source.load(response.data.content);
        this.dataPresent = true;
        if (!startup && !!response.data.content && response.data.content.length > 0) this.downloadPurchaseOrderReport(filter);
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if (!this.filterEntity.name) delete this.filterEntity.name
    if (!this.filterEntity.id) delete this.filterEntity.id
    if (!this.filterEntity.status) delete this.filterEntity.status
    this.fetchData(this.filterEntity);
  }

  private downloadPurchaseOrderReport(filter: any = {}): void {
    let blob: Observable<any> = this.reportService.downloadFilteredReport(DEFAULT_PAGE_REQUEST, this.format, 'purchase-order', filter);
    this.reportService.blobDownload(blob, 'List_of_purchase_order');
  }

  onSelectionChange(data): void {
    this.format = data;
  }

}
