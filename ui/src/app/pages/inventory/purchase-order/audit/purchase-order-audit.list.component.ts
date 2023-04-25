import {Component, Input, OnInit} from '@angular/core';
import {
  PurchaseOrder_AUDIT_ADD, PurchaseOrder_AUDIT_DELETE,
  PurchaseOrder_AUDIT_HEADER, PurchaseOrder_AUDIT_MODIFY,
  PurchaseOrderHistoryAudit,
} from "../purchase-order.data";
import {LocalDataSource} from "ng2-smart-table";
import {PurchaseOrderService} from "../purchase-order.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";

@Component({
  selector: 'inventory-purchase-order-audit-list',
  styleUrls: ['./purchase-order-audit.list.component.scss'],
  templateUrl: './purchase-order-audit.list.component.html',
})
export class PurchaseOrderAuditListComponent implements OnInit{

  settings = {
    hideSubHeader: true,
    actions: {
      position: 'right',
    },
    add: PurchaseOrder_AUDIT_ADD,
    edit: PurchaseOrder_AUDIT_MODIFY,
    delete: PurchaseOrder_AUDIT_DELETE,
    columns: PurchaseOrder_AUDIT_HEADER
  };

  filterEntity: any = {};
  @Input() entityId: number;


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private purchaseOrderService: PurchaseOrderService, private router:Router, private activatedRoute: ActivatedRoute,) {

  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/purchase-order/audit', this.entityId, 'revision', event.data.revisionId]);
  }

  private fetchData(id: number, filter: any = null): void {
    this.purchaseOrderService.getPurchaseOrderAudits(id, DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<PurchaseOrderHistoryAudit>> = p as GenericResponse<PageResponse<PurchaseOrderHistoryAudit>>;
        let data: PurchaseOrderHistoryAudit[] = response.data.content;
        this.source.load(data.map(obj => ({ ...obj, purchasedDate: obj.entity.purchasedDate })));
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
