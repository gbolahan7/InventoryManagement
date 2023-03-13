import {Component, Input, OnInit} from '@angular/core';
import {
  CategoryHistoryAudit,
} from "../category.data";
import {CategoryService} from "../category.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-category-audit-revision',
  styleUrls: ['./category-audit-revision.component.scss'],
  templateUrl: './category-audit-revision.component.html',
})
export class CategoryAuditRevisionComponent{

  categoryAudit: CategoryHistoryAudit = null;
  dataPresent: boolean  = false;

  constructor(private categoryService: CategoryService,
              private activatedRoute: ActivatedRoute,) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.fetchData(params.id, params.revisionId);
    });
  }

  private fetchData(id: number, revisionId): void {
    this.categoryService.getCategoryAudit(id, revisionId)
      .subscribe(((p: object) => {
        const response: GenericResponse<CategoryHistoryAudit> = p as GenericResponse<CategoryHistoryAudit>;
        this.categoryAudit = response.data;
        this.dataPresent = true;
      }), (error) => console.log(error));
  }


}
