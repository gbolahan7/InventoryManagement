import {Component, Input, OnInit} from '@angular/core';
import {
  CATEGORY_AUDIT_ADD, CATEGORY_AUDIT_DELETE,
  CATEGORY_AUDIT_HEADER, CATEGORY_AUDIT_MODIFY,
  CategoryHistoryAudit,
} from "../category.data";
import {LocalDataSource} from "ng2-smart-table";
import {CategoryService} from "../category.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-category-audit-list',
  styleUrls: ['./category-audit.list.component.scss'],
  templateUrl: './category-audit.list.component.html',
})
export class CategoryAuditListComponent implements OnInit{

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: CATEGORY_AUDIT_ADD,
    edit: CATEGORY_AUDIT_MODIFY,
    delete: CATEGORY_AUDIT_DELETE,
    columns: CATEGORY_AUDIT_HEADER
  };

  filterEntity: any = {};
  @Input() entityId: number;


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private categoryService: CategoryService, private router:Router, private activatedRoute: ActivatedRoute,) {

  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/category/audit', this.entityId, 'revision', event.data.revisionId]);
  }

  private fetchData(id: number, filter: any = null): void {
    this.categoryService.getCategoryAudits(id, DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<CategoryHistoryAudit>> = p as GenericResponse<PageResponse<CategoryHistoryAudit>>;
        let data: CategoryHistoryAudit[] = response.data.content;
        this.source.load(data.map(obj => ({ ...obj, name: obj.entity.name })));
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  filter(): void {
    this.filterEntity = !!this.filterEntity ? this.filterEntity : {};
    if(!this.filterEntity.revisionId) delete this.filterEntity.revisionId
    if(!this.filterEntity.revisionType) delete this.filterEntity.revisionType
    this.fetchData(this.entityId, this.filterEntity);
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: Params) => {
      let id: number = !!params.id ? params.id : this.entityId;
      this.fetchData(id);
      this.entityId = id;
    });
  }

}
