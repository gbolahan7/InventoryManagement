import { Component } from '@angular/core';
import {
  CATEGORY_REQUEST_ADD,
  CATEGORY_REQUEST_DELETE,
  CATEGORY_REQUEST_HEADER,
  CATEGORY_REQUEST_MODIFY,
  CategoryRequest
} from "../category.data";
import {LocalDataSource} from "ng2-smart-table";
import {CategoryService} from "../category.service";
import {Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-category-request-list',
  styleUrls: ['./category-request-list.component.scss'],
  templateUrl: './category-request-list.component.html',
})
export class CategoryRequestListComponent {

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: CATEGORY_REQUEST_ADD,
    edit: CATEGORY_REQUEST_MODIFY,
    delete: CATEGORY_REQUEST_DELETE,
    columns: CATEGORY_REQUEST_HEADER
  };

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private categoryService: CategoryService, private router:Router) {
    this.fetchData();
  }


  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/category/request', event.data.requestId]);
  }


  private fetchData(filter: any = {}): void {
    this.categoryService.getCategoryRequests(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<CategoryRequest>> = p as GenericResponse<PageResponse<CategoryRequest>>;
        this.source.load(response.data.content);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if(!this.filterEntity.name) delete this.filterEntity.name
    if(!this.filterEntity.requestStatus) delete this.filterEntity.requestStatus
    if(!this.filterEntity.requestType) delete this.filterEntity.requestType
    this.fetchData(this.filterEntity);
  }

}
