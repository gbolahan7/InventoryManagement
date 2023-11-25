import { Component } from '@angular/core';
import {CategoryService} from "../category.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Category, CategoryAudit, CategoryRequest} from "../category.data";

@Component({
  selector: 'inventory-category-view',
  styleUrls: ['./category-view.component.scss'],
  templateUrl: './category-view.component.html',
})
export class CategoryViewComponent {

  category: Category = null;
  dataPresent: boolean  = false;

  constructor(private categoryService: CategoryService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id);
    });
  }

  private fetchData(id: number): void {
    this.categoryService.getCategory(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<Category> = p as GenericResponse<Category>;
        this.category = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


}
