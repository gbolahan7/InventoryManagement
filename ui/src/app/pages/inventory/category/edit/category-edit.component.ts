import { Component } from '@angular/core';
import {CategoryService} from "../category.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Category, CategoryAudit, CategoryRequest} from "../category.data";
import {NbTagComponent} from "@nebular/theme";

@Component({
  selector: 'inventory-category-edit',
  styleUrls: ['./category-edit.component.scss'],
  templateUrl: './category-edit.component.html',
})
export class CategoryEditComponent {

  taskTags: string[] = [];
  taskInputTag :string = null;
  category: CategoryRequest = new CategoryRequest();
  dataPresent: boolean  = false;
  statuses = [{name: 'Active', key: 'Active'},{name: 'Inactive', key: 'Inactive'}];

  constructor(private categoryService: CategoryService, private router:Router, private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
    });
  }

  private fetchData(id: number): void {
    this.categoryService.getCategory(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<Category> = p as GenericResponse<Category>;
        this.category = this.mapFromCategoryToCategoryRequest(response.data);
        this.taskTags = this.category.items
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  mapFromCategoryToCategoryRequest(category: Category): CategoryRequest {
    let categoryRequest: CategoryRequest = JSON.parse(JSON.stringify(category))
    categoryRequest.categoryId = category.id;
    return categoryRequest;
  }
  onAddTaskTag(ref): void {
    if(ref && ref.value){
      if(!this.taskTags.includes(ref.value)) {
        this.taskTags.push(ref.value);
      }
      ref.value = '';
    }
  }

  onTaskProjectTag(ele: NbTagComponent): void {
    let value = ele.text;
    if(value) {
      const index = this.taskTags.indexOf(value);
      if (index > -1) {
        this.taskTags.splice(index, 1);
      }
    }
  }

  edit(): void {
    if(this.category &&
      !!this.category.name &&
      !!this.category.description && !!this.category.status ) {
      this.category.items = this.taskTags;
      this.editCategory(this.category);
    }

  }

  private editCategory(payload: any = {}): void {
    this.categoryService.editCategoryRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/inventory/category/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
