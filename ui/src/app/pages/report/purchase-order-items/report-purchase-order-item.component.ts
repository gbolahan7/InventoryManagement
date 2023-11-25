import {Component} from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../core/utils/template/http-util";
import {ReportService} from "../report.service";
import {Observable} from "rxjs";
import {
  PurchaseOrder,
  PurchaseOrder_HEADER,
  PurchaseOrderItem, PurchaseOrderItem_HEADER
} from "../../inventory/purchase-order/purchase-order.data";
import {PurchaseOrderService} from "../../inventory/purchase-order/purchase-order.service";

@Component({
  selector: 'report-purchase-order-item',
  styleUrls: ['./report-purchase-order-item.component.scss'],
  templateUrl: './report-purchase-order-item.component.html',
})
export class ReportPurchaseOrderItemComponent {

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
    columns: PurchaseOrderItem_HEADER
  };

  filterEntity: any = {};

  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private purchaseOrderService: PurchaseOrderService, private reportService: ReportService) {
    this.fetchData({}, true);
  }

  private fetchData(filter: any = {}, startup: boolean = false): void {
    this.purchaseOrderService.getPurchaseOrderItems(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<PurchaseOrderItem>> = p as GenericResponse<PageResponse<PurchaseOrderItem>>;
        this.source.load(response.data.content);
        this.dataPresent = true;
        if (!startup && !!response.data.content && response.data.content.length > 0) this.downloadPurchaseOrderItemReport(filter);
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if (!this.filterEntity.name) delete this.filterEntity.name
    if (!this.filterEntity.id) delete this.filterEntity.id
    if (!this.filterEntity.purchaseOrderId) delete this.filterEntity.purchaseOrderId
    if (!this.filterEntity.status) delete this.filterEntity.status
    this.fetchData(this.filterEntity);
  }

  private downloadPurchaseOrderItemReport(filter: any = {}): void {
    let blob: Observable<any> = this.reportService.downloadFilteredReport(DEFAULT_PAGE_REQUEST, this.format, 'purchase-order-item', filter);
    this.reportService.blobDownload(blob, 'List_of_purchase_order_item');
  }

  onSelectionChange(data): void {
    this.format = data;
  }

}
