import {Component, Input, OnInit} from '@angular/core';
import {
  PRODUCT_AUDIT_ADD, PRODUCT_AUDIT_DELETE,
  PRODUCT_AUDIT_HEADER, PRODUCT_AUDIT_MODIFY,
  ProductHistoryAudit,
} from "../product.data";
import {LocalDataSource} from "ng2-smart-table";
import {ProductService} from "../product.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-product-audit-list',
  styleUrls: ['./product-audit.list.component.scss'],
  templateUrl: './product-audit.list.component.html',
})
export class ProductAuditListComponent implements OnInit{

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: PRODUCT_AUDIT_ADD,
    edit: PRODUCT_AUDIT_MODIFY,
    delete: PRODUCT_AUDIT_DELETE,
    columns: PRODUCT_AUDIT_HEADER
  };

  filterEntity: any = {};
  @Input() entityId: number;


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private productService: ProductService, private router:Router, private activatedRoute: ActivatedRoute,) {

  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/product/audit', this.entityId, 'revision', event.data.revisionId]);
  }

  private fetchData(id: number, filter: any = null): void {
    this.productService.getProductAudits(id, DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<ProductHistoryAudit>> = p as GenericResponse<PageResponse<ProductHistoryAudit>>;
        let data: ProductHistoryAudit[] = response.data.content;
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
