import {Component} from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../core/utils/template/http-util";
import {ReportService} from "../report.service";
import {Observable} from "rxjs";
import {CategoryService} from "../../inventory/category/category.service";
import {Category, CATEGORY_HEADER} from "../../inventory/category/category.data";

@Component({
  selector: 'report-category',
  styleUrls: ['./report-category.component.scss'],
  templateUrl: './report-category.component.html',
})
export class ReportCategoryComponent {

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
    columns: CATEGORY_HEADER
  };

  filterEntity: any = {};

  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private categoryService: CategoryService, private reportService: ReportService) {
    this.fetchData({}, true);
  }

  private fetchData(filter: any = {}, startup: boolean = false): void {
    this.categoryService.getCategories(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<Category>> = p as GenericResponse<PageResponse<Category>>;
        this.source.load(response.data.content);
        this.dataPresent = true;
        if (!startup && !!response.data.content && response.data.content.length > 0) this.downloadCategoryReport(filter);
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if (!this.filterEntity.name) delete this.filterEntity.name
    if (!this.filterEntity.id) delete this.filterEntity.id
    if (!this.filterEntity.status) delete this.filterEntity.status
    this.fetchData(this.filterEntity);
  }

  private downloadCategoryReport(filter: any = {}): void {
    let blob: Observable<any> = this.reportService.downloadFilteredReport(DEFAULT_PAGE_REQUEST, this.format, 'category', filter);
    this.reportService.blobDownload(blob, 'List_of_categories');
  }

  onSelectionChange(data): void {
    this.format = data;
  }

}
