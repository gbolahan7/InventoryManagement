import { Component } from '@angular/core';
import {CategoryService} from "../category.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Category, CategoryAudit, CategoryRequest} from "../category.data";
import {NbTagComponent} from "@nebular/theme";

@Component({
  selector: 'inventory-category-create',
  styleUrls: ['./category-create.component.scss'],
  templateUrl: './category-create.component.html',
})
export class CategoryCreateComponent {

  taskTags: string[] = [];
  taskInputTag :string = null;
  category: Category = new Category();
  dataPresent: boolean  = false;
  statuses = [{name: 'Active', key: 'Active'},{name: 'Inactive', key: 'Inactive'}];

  constructor(private categoryService: CategoryService, private router:Router) {
    this.category.status = this.statuses[0].name;
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

  create(): void {
    if(this.category &&
      !!this.category.name &&
      !!this.category.description && !!this.category.status ) {
      this.category.items = this.taskTags;
      this.createCategory(this.category);
    }

  }

  private createCategory(payload: any = {}): void {
    this.categoryService.createCategoryRequest(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        this.router.navigate(['pages/inventory/category/request', response.data]);
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
