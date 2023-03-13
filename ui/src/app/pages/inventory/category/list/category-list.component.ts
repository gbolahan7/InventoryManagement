import { Component } from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {
  Category,
  CATEGORY_ADD,
  CATEGORY_CUSTOM,
  CATEGORY_DELETE,
  CATEGORY_HEADER,
  CATEGORY_MODIFY
} from "../category.data";
import {CategoryService} from "../category.service";
import {
  DEFAULT_PAGE_REQUEST,
  GenericResponse,
  PageResponse
} from "../../../../core/utils/template/http-util";
import {Router} from "@angular/router";
import {NbAccessChecker} from "@nebular/security";

@Component({
  selector: 'inventory-category-list',
  styleUrls: ['./category-list.component.scss'],
  templateUrl: './category-list.component.html',
})
export class CategoryListComponent {

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
      custom: CATEGORY_CUSTOM,
    },
    add: CATEGORY_ADD,
    edit: CATEGORY_MODIFY,
    delete: CATEGORY_DELETE,
    columns: CATEGORY_HEADER
  };

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private categoryService: CategoryService, private router:Router, accessChecker: NbAccessChecker) {
    accessChecker
      .isGranted('category', 'modify')
      .subscribe((value: boolean) => {
        if(!value) {
          this.settings.actions.custom = [{name: '', title: '<i style="display: flex;"></i>'}];
        }
      });
    this.fetchData();
  }

  onDeleteConfirm(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/category/list', event.data.id]);
  }


  private fetchData(filter: any = {}): void {
    this.categoryService.getCategories(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
          const response: GenericResponse<PageResponse<Category>> = p as GenericResponse<PageResponse<Category>>;
          this.source.load(response.data.content);
          this.dataPresent = true;
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if(!this.filterEntity.name) delete this.filterEntity.name
    if(!this.filterEntity.id) delete this.filterEntity.id
    if(!this.filterEntity.status) delete this.filterEntity.status
    this.fetchData(this.filterEntity);
  }

  onCustomAction(event) {
    switch ( event.action) {
      case CATEGORY_CUSTOM[0].name: //edit
        this.router.navigate(['pages/inventory/category/edit', event.data.id]);
        break;
    }
  }

}
