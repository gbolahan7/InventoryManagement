import { Component } from '@angular/core';
import {LocalDataSource} from "ng2-smart-table";
import {
  Unit,
  UNIT_ADD,
  UNIT_CUSTOM,
  UNIT_DELETE,
  UNIT_HEADER,
  UNIT_MODIFY
} from "../unit.data";
import {UnitService} from "../unit.service";
import {
  DEFAULT_PAGE_REQUEST,
  GenericResponse,
  PageResponse
} from "../../../../core/utils/template/http-util";
import {Router} from "@angular/router";
import {NbAccessChecker} from "@nebular/security";
import {ConfirmationDialogService} from "../../../../core/utils/confirm-dialog.service";

@Component({
  selector: 'inventory-unit-list',
  styleUrls: ['./unit-list.component.scss'],
  templateUrl: './unit-list.component.html',
})
export class UnitListComponent {

  settingsDelete = true;
  settings = this.fetchSettings();

  filterEntity: any = {};


  source: LocalDataSource = new LocalDataSource();

  dataPresent: boolean = false;

  constructor(private unitService: UnitService, private router:Router, accessChecker: NbAccessChecker, private confirmationService: ConfirmationDialogService) {
    accessChecker
      .isGranted('unit', 'delete')
      .subscribe((value: boolean) => {
        if(!value) {
          this.settingsDelete = false;
          this.settings = this.fetchSettings();
        }
      });
    this.fetchData();
  }

  onDeleteConfirm(event): void {
    this.confirmationService.confirm('Delete Unit', 'Are you sure you want to delete?')
      .then((confirmed: boolean) => {
        if (confirmed) {
          let value: Unit = event.data;
          this.unitService.deleteUnit(value.id).subscribe((event.confirm.resolve()), event.confirm.reject());
        } else {
          event.confirm.reject();
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  onClickEvent(event): void {
    this.router.navigate(['pages/inventory/unit/list', event.data.id]);
  }

  fetchSettings() {
    return {
      hideSubHeader: true,
      actions: {
        position: 'right',
        delete: this.settingsDelete,
        custom: UNIT_CUSTOM,
      },
      add: UNIT_ADD,
      edit: UNIT_MODIFY,
      delete: UNIT_DELETE,
      columns: UNIT_HEADER
    }
  }

  private fetchData(filter: any = {}): void {
    this.unitService.getUnits(DEFAULT_PAGE_REQUEST, filter)
      .subscribe(((p: object) => {
          const response: GenericResponse<PageResponse<Unit>> = p as GenericResponse<PageResponse<Unit>>;
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
      case UNIT_CUSTOM[0].name: //edit
        this.router.navigate(['pages/inventory/unit/edit', event.data.id]);
        break;
    }
  }

}
