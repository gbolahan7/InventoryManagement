import {Component} from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../core/utils/template/http-util";
import {ReportService} from "../report.service";
import {Observable} from "rxjs";
import {ProductService} from "../../inventory/product/product.service";
import {Product, PRODUCT_HEADER} from "../../inventory/product/product.data";

@Component({
  selector: 'report-product',
  styleUrls: ['./report-product.component.scss'],
  templateUrl: './report-product.component.html',
})
export class ReportProductComponent {

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
    columns: PRODUCT_HEADER
  };

  filterEntity: any = {};

  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private productService: ProductService, private reportService: ReportService) {
    this.fetchData({}, true);
  }

  private fetchData(filter: any = {}, startup: boolean = false): void {
    this.productService.getProducts(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<Product>> = p as GenericResponse<PageResponse<Product>>;
        this.source.load(response.data.content);
        this.dataPresent = true;
        if (!startup && !!response.data.content && response.data.content.length > 0) this.downloadProductReport(filter);
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if (!this.filterEntity.name) delete this.filterEntity.name
    if (!this.filterEntity.id) delete this.filterEntity.id
    if (!this.filterEntity.status) delete this.filterEntity.status
    this.fetchData(this.filterEntity);
  }

  private downloadProductReport(filter: any = {}): void {
    let blob: Observable<any> = this.reportService.downloadFilteredReport(DEFAULT_PAGE_REQUEST, this.format, 'product', filter);
    this.reportService.blobDownload(blob, 'List_of_product');
  }

  onSelectionChange(data): void {
    this.format = data;
  }

}
