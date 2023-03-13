import { Component } from '@angular/core';
import {CategoryService} from "../category.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Category, CategoryAudit, CategoryRequest} from "../category.data";

@Component({
  selector: 'inventory-category-request-view',
  styleUrls: ['./category-request-view.component.scss'],
  templateUrl: './category-request-view.component.html',
})
export class CategoryRequestViewComponent {

  categoryRequest: CategoryRequest = null;
  dataPresent: boolean  = false;

  APPROVE: string = 'Approve';
  REJECT: string = 'Reject'

  constructor(private categoryService: CategoryService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchDataRequest(params.id)
    });
  }

  private fetchDataRequest(id: number): void {
    this.categoryService.getCategoryRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<CategoryRequest> = p as GenericResponse<CategoryRequest>;
        this.categoryRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  onPermission(event, operation: string): void {
    if(operation === this.APPROVE) {
      this.approveDataRequest(this.categoryRequest.requestId);
    }else if(operation == this.REJECT) {
      this.rejectDataRequest(this.categoryRequest.requestId);
    }
  }

  private approveDataRequest(id: number): void {
    this.categoryService.approveCategoryRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<CategoryRequest> = p as GenericResponse<CategoryRequest>;
        this.categoryRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  private rejectDataRequest(id: number): void {
    this.categoryService.rejectCategoryRequest(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<CategoryRequest> = p as GenericResponse<CategoryRequest>;
        this.categoryRequest = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

}
